/*------------------------------------------------------------------------------
 *
 *    File:     knightstour-glib.c
 *    Date:     Tue Jan 12 19:29:44 2010
 *
 *    A Knight's Tour solver... using brute force
 *
 *------------------------------------------------------------------------------
 *
 *    Compile with:
 *     $> gcc -o knightstour-glib \
 *            `pkg-config --libs --cflags glib-2.0` \
 *            knightstour-glib.c
 *
 *    Run for example using a 5x5 board and starting in square [0,0]:
 *     $> ./knightstour-glib 5 0 0
 *
 *------------------------------------------------------------------------------
 *
 * Background (http://en.wikipedia.org/wiki/Knight%27s_tour):
 *
 *   The Knight's Tour is a mathematical problem involving a knight on a
 *   chessboard. The knight is placed on the empty board and, moving according
 *   to the rules of chess, must visit each square exactly once. A knight's tour
 *   is called a closed tour if the knight ends on a square attacking the square
 *   from which it began (so that it may tour the board again immediately with
 *   the same path). Otherwise the tour is open.
 *
 *   This solution should work for any SQUARE board size. If you want to give it
 *   a try, use 5 as square side size, you will get all possible paths in a
 *   couple of seconds...
 *
 *------------------------------------------------------------------------------
 */

/* Copyright (C) 2010 Aleksander Morgado */

/* This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

#include <glib.h>
#include <string.h>


/* A position is just the row/col coordinates */
typedef struct {
    gint8 row;
    gint8 col;
} Position;


/* Define all possible movements that could be done by the Knight */
#define MAX_ALLOWED_MOVEMENTS   8
static const Position _allowed_movements[MAX_ALLOWED_MOVEMENTS] = {
    { 1, 2},
    { 2, 1},
    { 2,-1},
    { 1,-2},
    {-1,-2},
    {-2,-1},
    {-2, 1},
    {-1, 2}
};


/* Timer to show how long it took to compute a given tour */
GTimer *_timer;
/* Square board size (N) */
guint8 _board_size;
/* Number of positions in the square (NxN) */
guint16 _max_positions_in_tour;
/* Starting row */
guint8 _start_row;
/* Starting column */
guint8 _start_col;


/* Check if coordinates are inside board */
#define __is_inside_board(row, col)              \
    ((row >= 0) &&                               \
     (row < _board_size) &&                      \
     (col >= 0) &&                               \
     (col < _board_size))


static gboolean
__check_inputs(const gchar *board_size_str,
               const gchar *row_str,
               const gchar *col_str);

static GNode *
__new_tour_node(guint row,
                guint col);
static void
__destroy_tour_node(GNode *node);

static void
__print_whole_tour(GNode *last_node);

static void
__populate_tour_node(GNode *node,
                     guint8 level);


int
main(gint argc, gchar **argv)
{
    /* Get number of incoming arguments. Must be 3 */
    if(argc != 4)
    {
        g_printf("\nUsage: %s <side_size> <start_row> <start_col>\n", argv[0]);
        g_printf("     (Rows and Columns from 0 to side_size-1)\n\n");
        return -1;
    }
    /* Check incoming arguments. They must be 2 integers */
    else if(! __check_inputs(argv[1],
                             argv[2],
                             argv[3]))
    {
        g_error("Invalid inputs. Can't go on.");
    }
    else
    {
        guint8 level = 1;
        GNode *tour = NULL;

        /* Initialize timer */
        _timer = g_timer_new();

        /* Create the initial position... */
        tour = __new_tour_node(_start_row,
                               _start_col);

        /* And start computing! */
        __populate_tour_node(tour, level);

        /* Clean exit */
        g_timer_destroy(_timer);
        __destroy_tour_node(tour);
    }

    return 0;
}


/* Allocate a new tour node in heap */
static GNode *
__new_tour_node(guint row,
                       guint col)
{
    GNode *node = NULL;
    Position *pos = NULL;

    /* Create the position... */
    pos = g_slice_alloc(sizeof(Position));
    pos->row = row;
    pos->col = col;

    /* Create new node with the given position */
    node = g_node_new(pos);

    return node;
}

/* Deallocate tour node from heap */
static void
__destroy_tour_node(GNode *node)
{
    if(node != NULL)
    {
        /* Once we got here, we don't need the Node and its children
         * any more */
        g_slice_free1(sizeof(Position), node->data);
        /* And remove from tree */
        g_node_destroy(node);
    }
}


/* Is this a new tour node or already exists? */
static gboolean
__is_a_new_tour_node(const GNode *previous_tour,
                     guint8 row,
                     guint8 col)
{
    gboolean found = FALSE;
    GNode *walker = (GNode *)previous_tour;

    /* Loop the branch of the tree upwards */
    while((!found) &&                           \
          (walker != NULL))
    {
        if((((Position *)(walker->data))->row == row) && \
           (((Position *)(walker->data))->col == col))
        {
            /* Found already in the tree... */
            found = TRUE;
        }
        else
        {
            /* Go up one level in the tree... */
            walker = walker->parent;
        }
    }

    return !found;
}

/* Recursive function to populate the nodes in the tree of nodes */
static void
__populate_tour_node(GNode *node,
                     guint8 level)
{
    if(node != NULL)
    {
        guint i;

        /* Loop for each possible allowed element */
        for(i=0; i<MAX_ALLOWED_MOVEMENTS; i++)
        {
            gint next_row;
            gint next_col;

            /* Compute next position in the tour.
             * Note that it may give negative values */
            next_row =                              \
                (((Position *)(node->data))->row) + \
                (_allowed_movements[i].row);
            next_col =                              \
                (((Position *)(node->data))->col) + \
                (_allowed_movements[i].col);

            /* If next position is outside the board, or already in the branch
             * of this tour, we just skip it */
            if((__is_inside_board(next_row,     \
                                  next_col)) && \
               (__is_a_new_tour_node(node,      \
                                     next_row,  \
                                     next_col)))
            {
                GNode *new_node;

                new_node = __new_tour_node(next_row,
                                           next_col);
                /* Insert the node in the tree */
                g_node_insert_after(node,       \
                                    NULL,       \
                                    new_node);

                /* Did we finally find a full knight's tour? */
                if(level == _max_positions_in_tour-1)
                {
                    __print_whole_tour(new_node);
                }
                else
                {
                    /* Now magic comes here, and we re-call this same function
                     * again for the newly created node */
                    __populate_tour_node(new_node,
                                         level+1);
                }

                /* Destroy the node */
                __destroy_tour_node(new_node);
            }
        } /* end for */
    }
}


/* Once a full tour has been found, print it */
static void
__print_whole_tour(GNode *last_node)
{
    guint i;
    gboolean is_closed = FALSE;
    GNode *walker;
    Position *whole_tour;

    /* Allocate array to store results */
    whole_tour = g_slice_alloc(sizeof(Position) * _max_positions_in_tour);

    /* Fill in array of positions */
    i = _max_positions_in_tour-1;
    walker = last_node;
    while((i >= 0) &&                           \
          (walker != NULL))
    {
        /* Copy value */
        memcpy(&whole_tour[i--],
               walker->data,
               sizeof(Position));

        /* Go to parent */
        walker = walker->parent;
    }

    /* Is it a closed tour? */
    if((whole_tour[_max_positions_in_tour-1].row == _start_row) && \
       (whole_tour[_max_positions_in_tour-1].col == _start_col))
    {
        is_closed = TRUE;
    }


    /* Finally print the whole tour */
    g_printf("(%lf) %s: ",
             g_timer_elapsed(_timer, NULL),
             is_closed ? "CLOSED" : "OPEN");
    for(i=0; i<_max_positions_in_tour; i++)
    {
        g_printf("[%u,%u]",
                 whole_tour[i].row,
                 whole_tour[i].col);
    }
    g_printf("\n");

    g_slice_free1(sizeof(Position) * _max_positions_in_tour,
                  whole_tour);
}


/* Check validity of input arguments, and if they are ok, initialize global
 * configuration */
static gboolean
__check_inputs(const gchar *board_size_str,
               const gchar *row_str,
               const gchar *col_str)
{
    gboolean ret_code = FALSE;
    if((row_str != NULL) &&                     \
       (col_str != NULL) &&                     \
       (board_size_str != NULL) &&              \
       (strlen(row_str) == 1) &&                \
       (strlen(col_str) == 1) &&                \
       (strlen(board_size_str) == 1) &&         \
       (isdigit(row_str[0])) &&                 \
       (isdigit(col_str[0])) &&                 \
       (isdigit(board_size_str[0])))
    {
        guint8 row;
        guint8 col;

        row = (guint8)atoi(row_str);
        col = (guint8)atoi(col_str);
        _board_size = (guint8)atoi(board_size_str);
        _max_positions_in_tour = _board_size*_board_size;

        if(__is_inside_board(row,col))
        {
            _start_row = row;
            _start_col = col;
            ret_code = TRUE;
        }
    }
    return ret_code;
}

/* End of knightstour-glib.c */
