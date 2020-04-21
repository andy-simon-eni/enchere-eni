$(document).ready(function(){
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
					var erreur = '<div class="notification is-danger">';
					erreur += '<button class="delete" onclick="closeError()"></button>';
					erreur += '<span>Le type de format n\'est pas accepté.</span>';
					erreur += '<br><br></div>';
					$(".erreurFile").append(erreur);
				}else if(data.result == "erreurSize"){
					var erreur = '<div class="notification is-danger">';
					erreur += '<button class="delete" onclick="closeError()"></button>';
					erreur += '<span>Le fichier est trop volumineux.</span>';
					erreur += '<br><br></div>';
					$(".erreurFile").append(erreur);
				}else{
					readURL(image);
				}	
			},
			error: function () {
				alert('pas marché');
			}
		});
	});
});

function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function (e) {
			$('.imgArticle, .imgArticleMobile').attr('src', e.target.result);
		};

		reader.readAsDataURL(input.files[0]);
	}
};

function closeError(){
	$(".erreurFile").children().remove();
}
