<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Innaxys - Path Finder</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
  	<link rel="stylesheet" href="/static/css/style.css">
  </head>
  <body>
  	<div class="container-fluid d-flex vh-100 flex-column py-3">
  	  <div class="row">
  	    <div class="col">
  	      <h1 class="text-center">Innaxys Path Finder</h1>
  	    </div>
  	  </div>
  	  <div class="row flex-fill fill d-flex">
  	    <div id="graph" class="col d-flex align-items-center justify-content-center">
  	      <svg id="graph" width="900" height="900"></svg>
  	    </div>
  	  </div>
  	  <div class="container-fluid d-flex justify-content-center align-items-center">
  	    <button class="btn btn-primary btn-lg me-3" disabled>Find Shortest Path</button>
  	    <button class="btn btn-info btn-lg me-3" data-bs-toggle="modal" data-bs-target="#modal" disabled>Statistics</button>
  	    <button class="btn btn-danger btn-lg" disabled>Clear</button>
  	  </div>
  	</div>
	<div id="background" class="position-fixed top-0 vw-100 vh-100 d-flex justify-content-center align-items-center">
	  <img class="img-fluid opacity-25 z-index w-25" src="https://www.innaxys.com/rw_common/images/Innaxys%20Logo%20large.png">
	</div>
	<div class="modal fade" id="modal" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h2 class="modal-title fs-3" id="modaLabel">Shortest Path Statistics</h2>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body fs-4"></div>
        </div>
      </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/d3@7"></script>
    <script src="/static/js/scripts.js"></script>
  </body>
</html>