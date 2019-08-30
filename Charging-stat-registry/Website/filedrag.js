(function() {
	// getElementById
	function $id(id) {
		return document.getElementById(id);
	}
	// output information
	function Output(msg) {
		var m = $id("messages");
		//m.innerHTML = msg + m.innerHTML;
	}
	// file drag hover

	function FileDragHover(e) {
		e.stopPropagation();
		e.preventDefault();
		e.target.className = (e.type == "dragover" ? "hover" : "");
	}
	// file selection
	function FileSelectHandler(e) {

		// cancel event and hover styling,, this prevents that behaviour from happening
		FileDragHover(e);

		// fetch FileList object
		var files = e.target.files || e.dataTransfer.files;
		upload(files);
		ImportJSONtoDatabase(files);

		
		// process all File objects
		for (var i = 0, f; f = files[i]; i++) {
			ParseFile(f);
		}
	}

	var upload = function (files) {

				console.log(files)

		// append the list of files using the form Data object
				var formData = new FormData(),
				xhr = new XMLHttpRequest(),
				x;

				for (x=0 ;  x<files.length; x= x+1)
				{
					formData.append('file[]', files[x]);
				}

				xhr.onload = function() {

					var data = this.responseText;
					console.log(data);

				}
				xhr.open('post','upload.php');
				xhr.send(formData);

				document.getElementById("Next").style.display = "block";

	}
	function ImportJSONtoDatabase(file){


	}


	// output file information
	function ParseFile(file) {

		Output(
			"<p>File information: <strong>" + file.name +
			"</strong> type: <strong>" + file.type +
			"</strong> size: <strong>" + file.size +
			"</strong> bytes</p>"
		);

	}


	// initialize
	function Init() {

			var fileselect = $id("fileselect"),


			filedrag = $id("filedrag"),
			submitbutton = $id("submitbutton");

		// file select
		fileselect.addEventListener("change", FileSelectHandler, false);

		// is XHR2 available?
		var xhr = new XMLHttpRequest();
		if (xhr.upload) {


			// file drop
			filedrag.addEventListener("dragover", FileDragHover, false);
			filedrag.addEventListener("dragleave", FileDragHover, false);
			filedrag.addEventListener("drop", FileSelectHandler, false);
			filedrag.style.display = "block";


			// remove submit button
			submitbutton.style.display = "none";
		}

	}

	// call initialization file
	if (window.File && window.FileList && window.FileReader) {
		Init();
	}


})();
