L.mapbox.accessToken = '<your access token here>';
var map = L.mapbox.map('map', 'examples.map-i86nkdio').setView(
		[ 38.89399, -77.03659 ], 17);

var featureGroup = L.featureGroup().addTo(map);

var drawControl = new L.Control.Draw({
	edit : {
		featureGroup : featureGroup
	},
	draw : {
		polygon : true,
		polyline : false,
		rectangle : false,
		circle : false,
		marker : false
	}
}).addTo(map);

map.on('draw:created', showPolygonArea);
map.on('draw:edited', showPolygonAreaEdited);

function showPolygonAreaEdited(e) {
	e.layers.eachLayer(function(layer) {
		showPolygonArea({
			layer : layer
		});
	});
}
function showPolygonArea(e) {
	featureGroup.clearLayers();
	featureGroup.addLayer(e.layer);
	e.layer.bindPopup((LGeo.area(e.layer) / 1000000).toFixed(2)
			+ ' km<sup>2</sup>');
	e.layer.openPopup();
}