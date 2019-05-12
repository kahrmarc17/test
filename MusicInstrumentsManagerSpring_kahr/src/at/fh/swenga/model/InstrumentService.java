package at.fh.swenga.model;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class InstrumentService {
 
	private List<InstrumentModel> instruments = new ArrayList<InstrumentModel>();


	/**
	 * 
	 * @param instrument
	 */
	public void addInstrument(InstrumentModel instrument) {
		instruments.add(instrument);
	}
 
	/**
	 * 
	 * @param instrument
	 * @return
	 */
	public boolean contains(InstrumentModel instrument) {
		return instruments.contains(instrument);
	}
 
	/**
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return instruments.isEmpty();
	}
 
	/**
	 * 
	 * @param id
	 * @return
	 */
	public InstrumentModel getInstrumentByid(int id) {
		for (InstrumentModel instrumentModel : instruments) {
			if (instrumentModel.getId() == id) {
				return instrumentModel;
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public List<InstrumentModel> getAllInstruments() {
		return instruments;
	}
 
	/**
	 * 
	 * @param searchString
	 * @return
	 */
	public List<InstrumentModel> getFilteredInstruments(String searchString) {
 
		if (searchString == null || searchString.equals("")) {
			return instruments;
		}
 
		// List for results
		List<InstrumentModel> filteredList = new ArrayList<InstrumentModel>();
 
		// checks every instrument
		for (InstrumentModel instrumentModel : instruments) {
 
			if ((instrumentModel.getCategory() != null && instrumentModel.getCategory().contains(searchString))
					|| (instrumentModel.getName() != null && instrumentModel.getName().contains(searchString))) {
				filteredList.add(instrumentModel);
			}
		}
		return filteredList;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	
	public boolean remove(int id) {
		return instruments.remove(new InstrumentModel(id, null, null, null, 0.0D, null, 0.0D, 0));
		
	}
}