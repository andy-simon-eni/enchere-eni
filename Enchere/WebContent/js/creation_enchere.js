$(document).ready(function(){
	var dt = new Date();
	dt.setDate( dt.getDate() + 1 );
	
	//Créer les deux calendrier de la page de création d'enchere
	$("[name='dateDebut'], [name='dateFin']").datepicker({
	    minDate: dt,
	    altFormat: "yy-mm-dd",
	    dateFormat: "yy-mm-dd",
	    firstDay: 1,
	    prevText: 'Précédent',
	    nextText: 'Suivant',
	    monthNames: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
	    dayNamesMin: ['Dim', 'Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam']
	});
	
	$("[name='dateDebut']").on("change", function(){
		var dt = new Date($(this).val());
		$("[name='dateFin']").datepicker('option', 'minDate', dt);
	});
	
	$("[name='dateFin']").on("change", function(){
		var dt = new Date($(this).val());
		$("[name='dateDebut']").datepicker('option', 'maxDate', dt);
	});
	
	$("#uploadImage").on("click", function(){
		$("#imgUrl").click();
	});
	
	$("#imgUrl").on("change", function(){
		image = this;
		var property = document.getElementById("imgUrl").files[0];
		
		$.ajax({
			url: "./upload_image",
			method: "POST",
			data: {
				name: property.name.split(".")[1],
				size: property.size,
			},
			dataType: 'json',
			success: function (data) {
				$(".erreurFile").children().remove();
				if(data.result == "erreurExt"){
					var erreur = '<div><div class="notification is-danger">';
					erreur += '<button class="delete" onclick="closeError()"></button>';
					erreur += '<span>Le type de format n\'est pas accepté.</span>';
					erreur += '</div><br></div>';
					$(".erreurFile").append(erreur);
				}else if(data.result == "erreurSize"){
					var erreur = '<div><div class="notification is-danger">';
					erreur += '<button class="delete" onclick="closeError()"></button>';
					erreur += '<span>Le fichier est trop volumineux.</span>';
					erreur += '</div><br></div>';
					$(".erreurFile").append(erreur);
				}else{
					readURL(image);
				}	
			}
		});
	});
});

//Affiche l'image
function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function (e) {
			$('.imgArticle, .imgArticleMobile').attr('src', e.target.result);
		};

		reader.readAsDataURL(input.files[0]);
	}
};

//Permet de fermer les erreurs
function closeError(){
	$(".erreurFile").children().remove();
}
