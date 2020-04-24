// HORS LIGNE

$("#selectCat").on("change", function() {
	$("#inputSearch").val("");
	$.ajax({
		url : "./recherchesNonConnecté",
		method : "POST",
		data : "search=" + $(this).val(),
		dataType : 'json',
		success : function(data) {
			initEnchere(data);
		}
	});
});

$("#inputSearch").on(
		"keyup",
		function() {
			var motCle = $(this).val().toLowerCase();
			$(".titleEnchere").each(
					function() {
						if ($(this).text().toLowerCase().includes(motCle)) {
							$(this).parent().parent().parent().parent().css(
									"display", "block");
						} else {
							$(this).parent().parent().parent().parent().css(
									"display", "none");
						}
					});
		});

// EN LIGNE

$(".rbEnchere").on("click", function() {
	$(".cbEnchere").prop("checked", false);
	$(".cbEnchere").prop("disabled", false);
	$(".cb" + $(this).data("val")).prop("disabled", true);
	
	searchEnchere($(this).val());
});

$(".cbEnchere").on("click", function() {
	$(".cbEnchere").not(this).prop("checked", false);
	searchEnchere($(this).val());
});

function searchEnchere(value, categ) {
	$.ajax({
		url : "./recherchesConnecté",
		method : "POST",
		data : {
			search: value,
			categorie: categ
			},
		dataType : 'json',
		success : function(data) {
			initEnchere(data);
		}
	});
}

// GLOBAL

function initEnchere(data) {
	$("#list").children().remove();
	for (var i = 0; i < data.length; i++) {
		var htmlContent = '<div class="column is-3">';
		htmlContent += '<div class="card" style="margin: 1em">';
		htmlContent += '<div class="card-image">';
		htmlContent += '<figure class="image is-4by3">';
		htmlContent += '<img src="https://bulma.io/images/placeholders/1280x960.png" alt="Placeholder image">';
		htmlContent += '</figure>';
		htmlContent += '</div>';
		htmlContent += '<div class="card-content">';
		htmlContent += '<div>';
		htmlContent += '<p class="titleEnchere title is-4">'
				+ data[i]["nomArticle"] + '</p>';
		htmlContent += '<span class="dateEnchere">' + data[i]["dateDebutEnch"]
				+ '</span>'
		htmlContent += '<p>Prix : ' + data[i]["montant"] + '</p>';
		htmlContent += '<p>Fin de l\'enchère : ' + data[i]["dateFinEnch"]
				+ '</p>';
		htmlContent += '<p>Vendeur : ' + data[i]["pseudo"] + '</p>';
		htmlContent += '</div></div></div></div>';

		$("#list").append(htmlContent);
	}
}