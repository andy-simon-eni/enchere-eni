// HORS LIGNE

$("#selectCat").on("change", function() {
	$("#inputSearch").val("");
	$.ajax({
		url : "./recherchesNonConnecté",
		method : "POST",
		data : "search=" + $(this).val(),
		dataType : 'json',
		success : function(data) {
			initEnchere(data, false);
		}
	});
});

// EN LIGNE

$("#selectCatConnecte").on("change", function() {
	$("#inputSearch").val("");
	var find = false;
	var value = "";
	
	$(".cbEnchere").each(function(){
		if($(this).prop("checked") == true){
			find = true;
			value = $(this).val();
		}
	});
	
	if(!find){
		$(".rbEnchere").each(function(){
			if($(this).prop("checked") == true){
				value = $(this).val();
			}
		});
	}
	
	searchEnchere(value, $(this).val());
	
});

$(".rbEnchere").on("click", function() {
	$("#inputSearch").val("");
	$(".cbEnchere").prop("checked", false);
	$(".cbEnchere").prop("disabled", false);
	$(".cb" + $(this).data("val")).prop("disabled", true);
	
	searchEnchere($(this).val(), $("#selectCatConnecte").val());
});

$(".cbEnchere").on("click", function() {
	$("#inputSearch").val("");
	$(".cbEnchere").not(this).prop("checked", false);
	var checked = false;
	$(".cbEnchere").each(function(){
		if($(this).prop("checked") == true){
			checked = true;
		}
	});
	
	if(checked){
		searchEnchere($(this).val(), $("#selectCatConnecte").val());
	}else{
		$(".rbEnchere").each(function(){
			if($(this).prop("checked") == true){
				$(this).click();
			}
		});
	}
	
	
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
			initEnchere(data, true);
		}
	});
}

// GLOBAL

function initEnchere(data, link) {
	$("#list").children().remove();
	for (var i = 0; i < data.length; i++) {
		var htmlContent = '<div class="column is-3">';
		htmlContent += '<div class="card is-margin-1em">';
		htmlContent += '<div class="card-image">';
		htmlContent += '<figure class="image is-4by3">';
		htmlContent += '<img src="https://bulma.io/images/placeholders/1280x960.png" alt="Placeholder image">';
		htmlContent += '</figure>';
		htmlContent += '</div>';
		htmlContent += '<div class="card-content">';
		htmlContent += '<div>';
		if(link){
			htmlContent += '<p class="titleEnchere title is-4"><a href="/Enchere/modifierEnchere?noArt='+data[i]["noArticle"]+'">'
			+ data[i]["nomArticle"] + '</a></p>';
		}else{
			htmlContent += '<p class="titleEnchere title is-4">'
			+ data[i]["nomArticle"] + '</p>';
		}
		
		htmlContent += '<span class="dateEnchere">' + data[i]["dateDebutEnch"]
				+ '</span>'
		htmlContent += '<p>Prix : ' + data[i]["montant"] + '</p>';
		htmlContent += '<p>Fin de l\'enchère : ' + data[i]["dateFinEnch"]
				+ '</p>';
		if(link){
			htmlContent += '<p>Vendeur : <a href="/Enchere/profil?pseudo='+data[i]["pseudo"]+'">' + data[i]["pseudo"] + '</a></p>';
		}else{
			htmlContent += '<p>Vendeur : ' + data[i]["pseudo"] + '</p>';
		}
		
		htmlContent += '</div></div></div></div>';

		$("#list").append(htmlContent);
	}
}

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