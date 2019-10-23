package kr.rapids.kosw.admin;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



/**
 * 에러 발생시 맵핑시키는 뷰리졸버 
 * @author kangnamgyu
 *
 */
//@ControllerAdvice(basePackageClasses = SampleController.class)
//@Controller
public class ErrorAdvice extends ResponseEntityExceptionHandler  {

//	@ResponseBody
//	ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
//		HttpStatus status = getStatus(request);
//		return new ResponseEntity<>(new Exception(ex), status);
//	}
	
	@ExceptionHandler(Exception.class)
	ModelAndView handleControllerException(HttpServletRequest request, Throwable ex) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		map.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
		map.put("message", ex.getClass().getName());
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		map.put("trace", sw.toString());
		
		return new ModelAndView("error/500", map);
	}
	

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}

}
