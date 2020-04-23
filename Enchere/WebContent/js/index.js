// HORS LIGNE

$("#selectCat").on("change", function(){
	searchDisconnected("categorie", $(this).val());
});

$("#inputSearch").on("keyup", function(){
	searchDisconnected("motCle", $(this).val());
});

function searchDisconnected(type, search){
	$.ajax({
		url: "./recherchesNonConnecté",
		method: "POST",
		data: {
			type: type,
			search: search,
		},
		dataType: 'json',
		success: function (data) {
			initEnchere(data);
		}
	});
}

//EN LIGNE

$(".rbEnchere").on("click", function(){
	$(".cbEnchere").prop("checked", false);
	$(".cbEnchere").prop("disabled", false);
	$(".cb"+$(this).data("val")).prop("disabled", true);
});

$(".cbEnchere").on("click", function(){
	$(".cbEnchere").not(this).prop("checked", false);
	
	alert($(this).val());

	$.ajax({
		url: "./recherchesConnecté",
		method: "POST",
		data: "search="+$(this).val(),
		dataType: 'json',
		success: function (data) {
			console.log(data);
			initEnchere(data);
		}
	});
});

//GLOBAL

function initEnchere(data){
	$("#list").children().remove();
	for (var i = 0; i < data.length; i++) {
		var htmlContent = '<div class="column is-3">';
		htmlContent+='<div class="card" style="margin: 1em">';
		htmlContent+='<div class="card-image">';
		htmlContent+='<figure class="image is-4by3">';
		htmlContent+='<img src="https://bulma.io/images/placeholders/1280x960.png" alt="Placeholder image">';
		htmlContent+='</figure>';
		htmlContent+='</div>';
		htmlContent+='<div class="card-content">';
		htmlContent+='<div>';
		htmlContent+='<p class="title is-4">'+data[i]["nomArticle"]+'</p>';
		htmlContent+='<p>Prix : '+data[i]["montant"]+'</p>';
		htmlContent+='<p>Fin de l\'enchère : '+data[i]["dateFinEnch"]+'</p>';
		htmlContent+='<p>Vendeur : '+data[i]["pseudo"]+'</p>';
		htmlContent+='</div></div></div></div>';

		$("#list").append(htmlContent);
	}
}