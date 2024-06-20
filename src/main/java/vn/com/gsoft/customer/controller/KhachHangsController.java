package vn.com.gsoft.customer.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.com.gsoft.customer.constant.PathContains;
import vn.com.gsoft.customer.model.dto.KhachHangsReq;
import vn.com.gsoft.customer.model.dto.MappingKhachHangReq;
import vn.com.gsoft.customer.model.dto.ThongTinKhuVucReq;
import vn.com.gsoft.customer.model.dto.ZaloOAReq;
import vn.com.gsoft.customer.model.system.BaseResponse;
import vn.com.gsoft.customer.service.KhachHangsService;
import vn.com.gsoft.customer.util.system.ResponseUtils;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping(PathContains.URL_KHACH_HANG)
public class KhachHangsController {
	
  @Autowired
  KhachHangsService service;


  @PostMapping(value = PathContains.URL_SEARCH_PAGE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colection(@RequestBody KhachHangsReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchPage(objReq)));
  }


  @PostMapping(value = PathContains.URL_SEARCH_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colectionList(@RequestBody KhachHangsReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchList(objReq)));
  }


  @PostMapping(value = PathContains.URL_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<BaseResponse> insert(@Valid @RequestBody KhachHangsReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.create(objReq)));
  }


  @PostMapping(value = PathContains.URL_UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<BaseResponse> update(@Valid @RequestBody KhachHangsReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.update(objReq)));
  }


  @GetMapping(value = PathContains.URL_DETAIL, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> detail(@PathVariable("id") Long id) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.detail(id)));
  }
  @PostMapping(value = PathContains.URL_DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> delete(@Valid @RequestBody KhachHangsReq idSearchReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.delete(idSearchReq.getId())));
  }

  @PostMapping(value = PathContains.URL_SEARCH_PAGE + "-nguoi-quan-tam-oa", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> searchNguoiQuanTamOA(@Valid @RequestBody ZaloOAReq req) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchPageFlowerOAByStoreCode(req)));
  }
  @PostMapping(value = PathContains.URL_UPDATE+ "-mapping-store", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<BaseResponse> updateMappingStore(@Valid @RequestBody MappingKhachHangReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.updateMappingStore(objReq)));
  }
  @PostMapping(value = PathContains.URL_UPDATE+ "-mapping-zalo-oa", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<BaseResponse> updateMappingZaloOa(@Valid @RequestBody MappingKhachHangReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.updateMappingZaloOA(objReq)));
  }
  @PostMapping(value = PathContains.URL_UPDATE+ "-thong-tin-khu-vuc", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<BaseResponse> updateMappingZaloOa(@Valid @RequestBody ThongTinKhuVucReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.updateThongTinKhuVuc(objReq)));
  }
  @PostMapping(value = PathContains.URL_RESTORE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> restore(@Valid @RequestBody KhachHangsReq idSearchReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.restore(idSearchReq.getId())));
  }

  @PostMapping(value = "/get-payment-score", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> getPaymentScore(@Valid @RequestBody KhachHangsReq idSearchReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.getPaymentScore(idSearchReq.getId())));
  }

  @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void exportList(@RequestBody KhachHangsReq objReq, HttpServletResponse response) throws Exception {
    try {
      service.export(objReq, response);
    } catch (Exception e) {
      log.error("Kết xuất danh sách dánh  : {}", e);
      final Map<String, Object> body = new HashMap<>();
      body.put("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      body.put("msg", e.getMessage());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding("UTF-8");
      final ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(response.getOutputStream(), body);
    }
  }

  @PostMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.importExcel(file)));
  }

}
