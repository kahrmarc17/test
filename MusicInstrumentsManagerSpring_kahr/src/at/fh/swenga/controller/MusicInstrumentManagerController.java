package at.fh.swenga.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.model.InstrumentModel;
import at.fh.swenga.model.InstrumentService;

@Controller
public class MusicInstrumentManagerController {

	@Autowired
	private InstrumentService instrumentService;

	@RequestMapping(value = { "/", "listInstruments" })
	public String showAllInstruments(Model model) {
		model.addAttribute("instruments", instrumentService.getAllInstruments());
		return "listInstruments";
	}

	// Fill with example data

	@RequestMapping("/fillInstrumentList")
	public String fillInstrumentList(Model model) {

		Date now = new Date();

		instrumentService.addInstrument(new InstrumentModel(1, "Trompete", "Yamaha YTR-3335",
				"Der naechste Schritt Zum ersten Mal hat Yamaha eine Schuelertrompete mit einem reversed Type Hauptstimmzug entwickelt, was sie in Sachen Spielgefuehl und Ansprache einmalig macht.",
				539.00, now, 1.50, 2));
		instrumentService
				.addInstrument(new InstrumentModel(2, "Schlagzeug", "DW PDP Mainstage BK",
						"14 x 05 Snare Drum,\r\n" + "22 x 16 Bass Drum,\r\n" + "10 x 08 Tom Tom,\r\n"
								+ "12 x 09 Tom Tom,\r\n" + "16 x 14 Stand Tom,\r\n" + "14 x 05 Snare Drum",
						1160.00, now, 20.00, 6));
		instrumentService.addInstrument(new InstrumentModel(3, "Posaune", "Yamaha YSL-448 G", "mit Quartventil,\r\n"
				+ "Goldmessing-Schallstueck,\r\n" + "L-Bohrung,\r\n" + "inkl. Koffer und 48 Mundstueck", 1617.00, now,
				2.50, 8));
		instrumentService.addInstrument(new InstrumentModel(4, "Klarinette", "Yamaha YCL-458-20",
				"Grenadillholzkorpus," + "20 Klappen, 6 Ringe,\r\n" + "versilberte Klappen,\r\n" + "Birne 56mm,\r\n"
						+ "inkl. Etui mit Rucksackgarnitur, Mundstueck und Yamaha Pflegeset",
				1278.00, now, 1.00, 4));
		instrumentService
				.addInstrument(new InstrumentModel(
						5, "E-Gitarre", "Ibanez GSA60-BS", "Agathis Korpus,\r\n" + "Ahorn Hals,\r\n"
								+ "22 Medium Buende,\r\n" + "Palisander Griffbrett,\r\n" + "Farbe: Brown Sunburst",
						199.00, now, 6.00, 6));
		instrumentService.addInstrument(new InstrumentModel(6, "Ukulelen", "Greg Bennet UK 50", "Concert size,\r\n"
				+ "Nato top, back, sides and neck,\r\n" + "Ebonized fingerboard,\r\n" + "Geared tuners", 115.00, now,
				0.90, 7));
		instrumentService.addInstrument(new InstrumentModel(7, "E-Pianos", "Roland GP-607PE",
				"Digitaler Stutzfluegel fuer den Wohnraum.\r\n"
						+ "Topaktuelle SuperNATURAL Piano-Modelingtechnologie fuer ein lebendiges Klangerlebnis.",
				4749.00, now, 100.00, 9));
		instrumentService.addInstrument(new InstrumentModel(8, "Keyboards", "Yamaha PSR-S775",
				"61 anschlagdynamische Tasten,\r\n" + "433 Styles,\r\n" + "128 stimmig Polyphonie,\r\n" + "DJ Styles",
				998.00, now, 15.00, 3));

		model.addAttribute("instruments", instrumentService.getAllInstruments());
		return "listInstruments";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}

	// Delete Instruments

	@GetMapping("/deleteInstrument")
	public String delete(Model model, @RequestParam int id) {
		boolean isRemoved = instrumentService.remove(id);

		if (isRemoved) {
			model.addAttribute("warningMessage", "Instrument with the ID " + id + " deleted");
		} else {
			model.addAttribute("errorMessage", "There is no Instrument with the ID: " + id);
		}

		return showAllInstruments(model);
	}

	// Search Instruments

	@PostMapping("/searchInstruments")
	public String search(Model model, @RequestParam String searchString) {
		model.addAttribute("instruments", instrumentService.getFilteredInstruments(searchString));
		return "listInstruments";
	}

	// Call the "Add Instrument" web page

	@GetMapping("/addInstrument")
	public String showAddInstrumentForm(Model model) {
		return "editInstrument";
	}

	// Fill some new instruments

	@PostMapping("/addInstrument")
	public String addInstrument(@Valid InstrumentModel newInstrumentModel, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid: " + fieldError.getCode() + "<br>";
			}
			model.addAttribute("errorMessage", errorMessage);

			return "forward:/listInstruments";
		}

		InstrumentModel instrument = instrumentService.getInstrumentByid(newInstrumentModel.getId());

		if (instrument != null) {
			model.addAttribute("errorMessage", "Instrument already exists!<br>");
		} else {
			instrumentService.addInstrument(newInstrumentModel);
			model.addAttribute("message", "New instrument " + newInstrumentModel.getId() + " added.");
		}

		return "forward:/listInstruments";
	}

	// Show the "Edit Instrument" web page

	@GetMapping("/editInstrument")
	public String showChangeInstrumentForm(Model model, @RequestParam int id) {

		InstrumentModel instrument = instrumentService.getInstrumentByid(id);

		if (instrument != null) {
			model.addAttribute("instrument", instrument);
			return "editInstrument";
		} else {
			model.addAttribute("errorMessage", "Couldn't find instrument with the ID " + id);
			return "forward:/listInstruments";
		}
	}

	// Edit the Instruments

	@PostMapping("/editInstrument")
	public String editInstrument(@Valid InstrumentModel changedInstrumentModel, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid: " + fieldError.getCode() + "<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
			return "forward:/listInstruments";
		}

		InstrumentModel instrument = instrumentService.getInstrumentByid(changedInstrumentModel.getId());

		if (instrument == null) {
			model.addAttribute("errorMessage", "Instrument does not exist!<br>");
		} else {

			// Change the attributes
			instrument.setId(changedInstrumentModel.getId());
			instrument.setCategory(changedInstrumentModel.getCategory());
			instrument.setName(changedInstrumentModel.getName());
			instrument.setDescription(changedInstrumentModel.getDescription());
			instrument.setPrice(changedInstrumentModel.getPrice());
			instrument.setDateofavailability(changedInstrumentModel.getDateofavailability());
			instrument.setWeight(changedInstrumentModel.getWeight());
			instrument.setAmount(changedInstrumentModel.getAmount());

			model.addAttribute("message", "Changed instrument with the ID " + changedInstrumentModel.getId());
		}

		return "forward:/listInstruments";
	}

}
