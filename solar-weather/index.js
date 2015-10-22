
function on_tilt(e) {
	var tiltLR = e.gamma;
	var tiltFB = e.beta;
	
	// document.getElementById("tiltLR").innerHTML = tiltLR;
	// document.getElementById("tiltFB").innerHTML = tiltFB;
	
	var newRotationMatrix = mat4.create();
	mat4.identity(newRotationMatrix);
	mat4.rotate(newRotationMatrix, degToRad(-0.5 * tiltLR), [0, 1, 0]);

	mat4.rotate(newRotationMatrix, degToRad(-0.5 * tiltFB), [1, 0, 0]);

	// mat4.multiply(newRotationMatrix, moonRotationMatrix, moonRotationMatrix);
	moonRotationMatrix = newRotationMatrix;

}

window.addEventListener('deviceorientation', on_tilt, false);