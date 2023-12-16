<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>


<script type="text/javascript" src="/assets/js/validate.js"></script>


<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;" onkeydown="javascript:onEnterLogin();">
<script>
function onEnterLogin(){
	var keyCode = window.event.keyCode;
	
	if (keyCode == 13) {
		loginCHK();
	}
}
</script>
<div class="container-fluid h-custom">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-md-9 col-lg-6 col-xl-5">
        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
          class="img-fluid" alt="Sample image">
      </div>
      <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
        <form action="sign-in.do" method="post" name="login">
          <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start">
            <p class="lead fw-normal mb-0 me-3">Sign in with</p>
            <div class="btn btn-primary btn-floating mx-1 btn-google btn-user btn-block" onclick="location.href='/oauth2/authorization/google'">
            	<i class="fab fa-google fa-fw"></i> Google
            </div>
          </div>

          <div class="divider d-flex align-items-center my-4">
            <p class="text-center fw-bold mx-3 mb-0">Or</p>
          </div>

          <!-- Email input -->
          <div class="form-outline mb-4">
            <input type="text" id="id" name="id" class="form-control form-control-lg"
              placeholder="Enter ID" autofocus="autofocus" />
          </div>

          <!-- Password input -->
          <div class="form-outline mb-3">
            <input type="password" id="password" name="password" class="form-control form-control-lg"
              placeholder="Enter password" />
          </div>

          <div class="d-flex justify-content-between align-items-center">
            <!-- Checkbox -->
            <div class="form-check mb-0">
              <input class="form-check-input me-2" type="checkbox" value="true" id="tf" name="tf" />
              <label class="form-check-label" for="form2Example3">
                Remember me
              </label>
            </div>
          </div>

          <div class="text-center text-lg-start mt-4 pt-2">
            <input type="button" class="btn btn-primary btn-lg"
              style="padding-left: 2.5rem; padding-right: 2.5rem;" onclick="loginCHK();" value="Login">
            <p class="small fw-bold mt-2 pt-1 mb-0">Don't have an account? <a href="sign-up.do"
                class="link-danger">Register</a></p>
          </div>

        </form>
      </div>
    </div>
  </div>
</section>



<%@ include file="footer.jsp"%>