//package global.citytech.remitpulse.countries.repositories.internal;
//
//import global.citytech.remitpulse.countries.repositories.AuditInfoRepository;
//import global.citytech.remitpulse.countries.repositories.CountryRepository;
//import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
//import global.citytech.rabbit.core.audits.AuditLogInfo;
//
//import javax.inject.Inject;
//
///** @author bipin on 6/25/19 12:01 PM. */
//public class AuditInfoRepositoryImpl implements AuditInfoRepository {
//
//  private CountryRepository countryRepository;
//
//  @Inject
//  public AuditInfoRepositoryImpl(CountryRepository countryRepository) {
//    this.countryRepository = countryRepository;
//  }
//
//  @Override
//  public AuditLogInfo add(CountryInfo countryInfo) {
//      AuditLogInfo auditLogInfo=this.countryRepository.create(countryInfo);
//      if (!checkIfMakerCheckerEnabled(auditLogInfo)){
//          this.countryRepository.approve(auditLogInfo);
//      }
//    return auditLogInfo;
//  }
//
//    @Override
//    public AuditLogInfo update(CountryInfo countryInfo) {
//      AuditLogInfo auditLogInfo= this.countryRepository.update(countryInfo);
//      if (!checkIfMakerCheckerEnabled(auditLogInfo)){
//        this.countryRepository.approve(auditLogInfo);
//      }
//      return auditLogInfo;
//    }
//
//    private boolean checkIfMakerCheckerEnabled(AuditLogInfo auditLogInfo){
//      return false;
//  }
//}
