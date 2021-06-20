package global.citytech.remitpulse.countries.crudapi.impl.helper;

/** @author sunil */
public interface RepositoryHelper<I, O> {
  O execute(I helperInput);
}
