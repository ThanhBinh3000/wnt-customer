package vn.com.gsoft.customer.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.gsoft.customer.constant.PathContains;
import vn.com.gsoft.customer.model.dto.KhachHangsReq;
import vn.com.gsoft.customer.model.dto.MappingKhachHangReq;
import vn.com.gsoft.customer.model.dto.ZaloOAReq;
import vn.com.gsoft.customer.model.system.BaseResponse;
import vn.com.gsoft.customer.service.KhachHangsService;
import vn.com.gsoft.customer.util.system.ResponseUtils;


@Slf4j
@RestController
@RequestMapping(PathContains.URL_KHACH_HANG)
public class KhachHangsController {
	
  @Autowired
  KhachHangsService service;


  @PostMapping(value = PathContains.URL_SEARCH_PAGE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colection(@RequestBody KhachHangsReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchCustomerManagementPage(objReq)));
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
}
