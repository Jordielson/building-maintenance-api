package com.manutencao_predial;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.manutencao_predial.model.Company;
import com.manutencao_predial.model.Supplier;
import com.manutencao_predial.model.User;
import com.manutencao_predial.repository.BuildingMaterialRepository;
import com.manutencao_predial.repository.CompanyRepository;
import com.manutencao_predial.repository.ImmobileRepository;
import com.manutencao_predial.repository.SupplierRepository;
import com.manutencao_predial.repository.UserRepository;

@SpringBootTest
public class InterfaceTest {
	
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static long TIMEOUT_IN_SECONDS = 2;
	
	private WebElement inputEmail;
	private WebElement inputPassword;
	private WebElement submit;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ImmobileRepository immobileRepository;
	
	@Autowired
	SupplierRepository supplierRepository;

	@Autowired
	BuildingMaterialRepository buildingMaterialRepository;
	

	@BeforeAll
	public static void configura() {
		System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver"); //CUIDADO COM A VERSÃO DO DRIVE VS VERSÃO DO CHROME: https://sites.google.com/a/chromium.org/chromedriver/downloads
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);
	}
	
	@BeforeEach
	public void login() {
		driver.get("http://localhost:19006/");
		inputEmail = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[2]/div[1]/input"));
		inputPassword = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[3]/div[1]/input"));
		submit = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[4]/div/div/div/div[2]"));
	}
	
	@AfterAll
	public static void finaliza() {
		driver.quit();
	}

    @Test
    public void gerenteTest() throws InterruptedException {
		inputEmail.sendKeys("joao@gmail.com");
        waitScreen();
		inputPassword.sendKeys("123456");
        waitScreen();
		
		submit.click();
        waitScreen();
        
        WebElement titulo = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[2]/div[2]/div/h1"));
        assertEquals("Principal Gerente", titulo.getText());
        assertNotEquals("Principal ADM", titulo.getText());
        assertNotEquals("Principal Chefe de Setor", titulo.getText());
        assertNotEquals("Principal Prestador", titulo.getText());
		
		/**Finilizar servico */
		String tituloNotif;
        WebElement notificacao = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[1]/div/div/div/div/div/div/div[1]/div/div"));
		tituloNotif = notificacao.getText();
		notificacao.click();
        waitScreen();
		
        WebElement inputOrcamento = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/div[1]/div/div/div/div[7]/div[1]/input"));
		inputOrcamento.sendKeys("25.56");
		
        WebElement prazo = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/div[1]/div/div/div/div[8]/input"));
		prazo.sendKeys("2021-12-16");

        WebElement btnSalvar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/div[1]/div/div/div/div[10]/div/div")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", btnSalvar);
		waitScreen();

		Alert alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "Serviço cadastro com sucesso");
		alert.accept();
		waitScreen();
		
        WebElement servicos = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[2]/div/a[2]/span"));
		servicos.click();
		waitScreen();
		
        WebElement servico = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div/div/div/div[2]/div/div/div/div"));
		assertEquals(servico.getText(), tituloNotif + " - EXECUTANDO");
		servico.click();
		waitScreen();
		
		/**Escolher Prestador */
        WebElement prestador = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/div[1]/div/div[2]/div/div[1]/div/div"));
		String nomePrestador = prestador.getText();
		prestador.click();
		waitScreen();

		alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "Prestador " + nomePrestador + " foi acionado!");
		alert.accept();

        WebElement voltar = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/div[2]/div/div[2]/div[2]/div[1]/div/div/img"));
		voltar.click();
		waitScreen();

		/**Estoque */
        WebElement estoque = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[2]/div/a[3]/span"));
		estoque.click();
		waitScreen();

		/**Cadastrar Produto */	
        WebElement btnCadastrarProduto = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[1]/div[3]/div/div/div/div[2]/div"));
		btnCadastrarProduto.click();
		waitScreen();

        WebElement inputNomeProd = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/div[1]/div/div/div/div[2]/div[1]/input"));
		inputNomeProd.sendKeys("Lâmpada");

		WebElement inputValorProd = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/div[1]/div/div/div/div[3]/div[1]/input"));
		inputValorProd.sendKeys("25.52");

        WebElement inputQtd = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/div[1]/div/div/div/div[4]/div[1]/input"));
        inputQtd.sendKeys("20");

		WebElement btnCadastrar = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/div[1]/div/div/div/div[5]/div/div/div"));
		btnCadastrar.click();
		waitScreen();

		alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "Produto Lâmpada cadastrado! ");
		alert.accept();
		
		estoque = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[2]/div/a[3]/span"));
		estoque.click();
		waitScreen();

		
		inputQtd = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div/div/div/div[1]/div/div[1]/div/div[2]/span/input"));
		WebElement aumetarQtd = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div/div/div/div[1]/div/div[1]/div/div[2]/span/b[1]/i"));
		aumetarQtd.click();
		aumetarQtd.click();
		waitScreen();
		
		assertEquals(inputQtd.getAttribute("value"), "22");

		WebElement diminuirQtd = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div/div/div/div[1]/div/div[1]/div/div[2]/span/b[2]"));
		diminuirQtd.click();
		diminuirQtd.click();
		waitScreen();
		
		assertEquals(inputQtd.getAttribute("value"), "20");

		/**Sair */
		WebElement perfil = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[2]/div/a[4]"));
		perfil.click();
		waitScreen();

		WebElement sair = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[1]/div/div[1]/div[3]/div/div/div/div[8]/div/div/div/div[2]"));
		sair.click();
		waitScreen();

		titulo = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[2]/div[2]/div/h1"));
		assertEquals(titulo.getText(), "Login");
    }
    
    @Test
    public void ADMTest() throws InterruptedException  {

		inputEmail.sendKeys("agemiro@email.com");
        waitScreen();

		inputPassword.sendKeys("123456");
        waitScreen();

		submit.click();
        waitScreen();
        
        WebElement titulo = driver.findElement(By.xpath("//*[@id=\"animatedComponent\"]"));
        
        assertEquals("Principal ADM", titulo.getText());
        
        assertNotEquals("Principal Gerente", titulo.getText());
        
        assertNotEquals("Principal Chefe de Setor", titulo.getText());
        
        assertNotEquals("Principal Prestador", titulo.getText());

/*Teste de cadastro de empresa*/
        
        WebElement cadastrarEmpresa = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div[1]/div[2]/div[2]/div/div/div/div[1]/div[1]/div/div/div/div/div/div[2]/div/div/div[2]"));     
        cadastrarEmpresa.click();
        waitScreen();

        WebElement inputNomeEmpresa = driver.findElement(By.xpath("//*[@id=\"animatedComponent\"]/input"));        
        inputNomeEmpresa.sendKeys("Empresa Teste");
        waitScreen();

        WebElement cnpjEmpresa = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/div[1]/div/div/div/div[3]/input"));        
        cnpjEmpresa.sendKeys("8282828");
        waitScreen();

        WebElement cadastrarEmpresa2 = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/div[1]/div/div/div/div[4]/div/div/div"));        
        cadastrarEmpresa2.click();
        waitScreen();

        Optional<Company> company = companyRepository.findById("8282828");
        
        assertTrue(company!=null);
        
/*Teste de cadastro de usuário*/
        
        WebElement cadastrarUsuario = driver.findElement(By.xpath(""));
        cadastrarUsuario.click();
        waitScreen();
        
        WebElement email = driver.findElement(By.xpath(""));        
        email.sendKeys("email@email.com");
        waitScreen();

        WebElement nome = driver.findElement(By.xpath(""));        
        nome.sendKeys("teste");
        waitScreen();

        WebElement cpf = driver.findElement(By.xpath(""));        
        cpf.sendKeys("12190052472");
        waitScreen();
        
        WebElement cargo = driver.findElement(By.xpath(""));        
        cargo.findElement(By.xpath("")).click();
        waitScreen();
        
        WebElement senha = driver.findElement(By.xpath(""));        
        senha.sendKeys("123456");
        waitScreen();
        
        WebElement btnCadastrar = driver.findElement(By.xpath(""));        
        btnCadastrar.click();
        waitScreen();
        
        List<User> user = userRepository.findByEmail("email@email.com");
        
        assertTrue(user != null);
        
/*Teste de cadastro de imovel*/
        
        WebElement cadastrarImovel= driver.findElement(By.xpath(""));
        cadastrarImovel.click();
        waitScreen();
        
        WebElement nomeImovel = driver.findElement(By.xpath(""));
        nomeImovel.sendKeys("imovel1");
        waitScreen();
        
        WebElement Endereco = driver.findElement(By.xpath(""));        
        Endereco.sendKeys("rua fulano de tal");
        waitScreen();

        WebElement porte = driver.findElement(By.xpath(""));        
        porte.click();
        waitScreen();
        
        WebElement btnContinuar = driver.findElement(By.xpath(""));        
        btnContinuar.click();
        waitScreen();

        WebElement numeroAndar = driver.findElement(By.xpath(""));        
        numeroAndar.sendKeys("1");
        waitScreen();
        
        WebElement descricao = driver.findElement(By.xpath(""));        
        descricao.sendKeys("andar teste");
        waitScreen();
        
        WebElement btnCadastrar1 = driver.findElement(By.xpath(""));        
        btnCadastrar1.click();
        waitScreen();
        
        /* Está faltando uma logica para saber se o imovel foi adicionado no banco de dados (FindBy... e assertEquals) */
       
        
/*Teste de cadastro de fornecedor*/
        
        WebElement cadastrarFornecedor = driver.findElement(By.xpath(""));
        cadastrarFornecedor.click();
        waitScreen();
        
        WebElement nomeFornecedor = driver.findElement(By.xpath(""));
        nomeFornecedor.sendKeys("fornecedor1");
        waitScreen();
        
        WebElement cnpj = driver.findElement(By.xpath(""));        
        cnpj.sendKeys("12345678912");
        waitScreen();

        WebElement btnCadastrarFornecedor = driver.findElement(By.xpath(""));        
        btnCadastrarFornecedor.click();
        waitScreen();
        
        Optional<Supplier> supplier = supplierRepository.findById("12345678912");
        
        assertTrue(user != null);
        
    }
    
    @Test
	public void chefeSetorTest() throws InterruptedException  {

		inputEmail.sendKeys("jord@email.com");
        waitScreen();
		inputPassword.sendKeys("123456");
        waitScreen();
		
		submit.click();
        waitScreen();
        
        WebElement titulo = driver.findElement(By.xpath("//*[@id=\"animatedComponent\"]"));
        
        assertEquals("Principal Chefe de Setor", titulo.getText());

        assertNotEquals("Principal ADM", titulo.getText());
        
        assertNotEquals("Principal Gerente", titulo.getText());
                
        assertNotEquals("Principal Prestador", titulo.getText());
        
        waitScreen();
	}

    @Test
	public void prestadorTest() throws InterruptedException  {

		inputEmail.sendKeys("pedro@email.com");
        waitScreen();
		inputPassword.sendKeys("123456");
        waitScreen();
		
		submit.click();
        waitScreen();
        
        WebElement titulo = driver.findElement(By.xpath("//*[@id=\"animatedComponent\"]"));
        
        assertEquals("Principal Prestador", titulo.getText());

        assertNotEquals("Principal ADM", titulo.getText());
        
        assertNotEquals("Principal Gerente", titulo.getText());
        
        assertNotEquals("Principal Chefe de Setor", titulo.getText());
                
        waitScreen();
        
	}
	
	// @Test
	// public void testGoogle() throws InterruptedException {
	// 	driver.get("https://www.google.com/");
		
	// 	WebElement searchArea = driver.findElement(By.cssSelector("input.gLFyf.gsfi"));
	// 	searchArea.sendKeys("G1");
	// 	waitScreen();
	// 	searchArea.sendKeys(Keys.ENTER);
	// 	waitScreen();
		
		
	// 	WebElement g1Site = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[1]/div/div/div/div/div/div/div[1]/a/h3"));
	// 	g1Site.click();
	// 	waitScreen();
		
	// 	assertEquals("https://g1.globo.com/", driver.getCurrentUrl());
		
	// 	WebElement ge = driver.findElement(By.xpath("//*[@id=\"barra-globocom\"]/div/div/ul/li[2]/a"));
	// 	ge.click();
	// 	waitScreen();
		
	// 	assertEquals("https://ge.globo.com/", driver.getCurrentUrl());
		
		
	// 	WebElement cano = driver.findElement(By.xpath("//*[@id=\"fc4c6f60-f2d8-4751-9bce-798404e9e79a\"]/div/div[2]/div/a"));
	// 	assertEquals("Fluminense avança nas negociações com Cano", cano.getText());
	// 	waitScreen();
	// 	cano.click();
	// 	waitScreen();
		
	// 	WebElement canoNews = driver.findElement(By.className("content-head__title"));
	// 	assertEquals("Fluminense avança nas negociações com atacante Germán Cano, ex-Vasco", canoNews.getText());
		
		
	// 	waitScreen();
	// 	driver.navigate().back();
	// 	waitScreen();
	// 	driver.navigate().forward();
	// 	waitScreen();
		
		
	// }
	
//	@Test
//	public void testGoogle() throws InterruptedException {
//		driver.get("https://www.google.com/");
//		
//		WebElement linkOlpBt = driver.findElement(By.className("NKcBbd"));
//		linkOlpBt.click();
//		
//		assertEquals("Olimpiadas - Pesquisa Google", driver.getTitle());
//		waitScreen();
//		
////		WebElement nameOlp = driver.findElement(By.className("u9DLmf"));
////		WebElement nameOlp = driver.findElement(By.cssSelector("span.u9DLmf"));
//		WebElement nameOlp = driver.findElement(By.xpath("//*[@id=\"rcnt\"]/div[1]/div[2]/div/div/div[3]/div[1]/div/div[2]/div/div/div/div[1]/span"));
//		assertEquals("Jogos Olímpicos Tóquio 2020", nameOlp.getText());
//		
//		WebElement marchaAt = driver.findElement(By.cssSelector("span.tsp-db.tsp-f16.tsp-fbr.tsp-tlel"));
//		marchaAt.click();
//		
//		waitScreen();
//		
//		WebElement atleta = driver.findElement(By.xpath("/html/body/div[7]/div/div[5]/div/div[2]/span/div/div/div[4]/div[1]/div/div[1]/div/div[2]/div/div[2]/table/tbody/tr[2]/td[1]/div/div/div/div[1]/a/span"));
//		assertEquals("K. Hayward", atleta.getText());
//		atleta.click();
//		waitScreen();
//		
//		
//		WebElement atletaWiki = driver.findElement(By.cssSelector("a.ruhjFe.NJLBac.fl"));
//		atletaWiki.click();
//		waitScreen();
//		
//		assertEquals("https://en.wikipedia.org/wiki/Katie_Hayward", driver.getCurrentUrl()); 
//
//	}
	
	
	private void waitScreen() throws InterruptedException {
		Thread.sleep(2000);
	}
	
	

//	@Test
//	public void testInvestingListWebElement() throws InterruptedException {
//		driver.get("https://br.investing.com/");
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-accept-btn-handler")));
////		Thread.sleep(2000);
//		
//		
//		WebElement botaoChato = driver.findElement(By.id("onetrust-accept-btn-handler"));
//		botaoChato.click();
//		Thread.sleep(2000);
//		
//		WebElement areaBusca = driver.findElement(By.className("searchText"));
//		areaBusca.sendKeys("PETR4");
//		Thread.sleep(2000);//less than 2 sec can change the results
//		
//		List<WebElement> listaAcoes = driver.findElements(By.className("js-table-results"));
//		WebElement petro = listaAcoes.get(0);
//		String nomePetro = petro.findElement(By.className("second")).getText();
//		String nomeCompletoPetro = petro.findElement(By.className("third")).getText();
//		Thread.sleep(3000);
//		
//		
//		
//		
//		assertEquals("PETR4", nomePetro);
//		assertEquals("PETROBRAS Petroleo Brasileiro SA PN", nomeCompletoPetro);
//	}
	
	
	
	
	
//	@Test
//	public void testGoogle() throws InterruptedException {
//		driver.get("https://www.google.com/");
//		
//		WebElement linkSup = driver.findElement(By.className("lnXdpd"));
//		linkSup.click();
//		Thread.sleep(1000);
//		
//		WebElement covidText = driver.findElement(By.className("hAPCZc"));
//		assertEquals("COVID-19", covidText.getText());
//		
//		
////		List<WebElement> numberVac = driver.findElements(By.className("fUyIqc"));
////		assertEquals("146 mi", numberVac.getText());
////		System.out.println(">" +  numberVac.getText());
//		
//		WebElement numberVac = driver.findElement(By.xpath("//*[@id=\"rhs\"]/div[3]/div/div/div/div/div/div/div/div/div[2]/div[1]/div/div[1]/table/tbody/tr[2]/td[1]/div[1]/div[1]/span"));
//		assertEquals("146 mi", numberVac.getText());
////		Thread.sleep(1000);
//		
//		WebElement primNew = driver.findElement(By.className("xCURGd"));
//		primNew.click();
//		
//		assertEquals("Doodle do Google ajuda a achar o posto de vacinação mais próximo - Notícias - R7 Tecnologia e Ciência", driver.getTitle());
//	}
	
//	@Test
//	public void testInvesting() throws InterruptedException {
//		driver.get("https://br.investing.com/");
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-accept-btn-handler")));
////		Thread.sleep(2000);
//		
//		
//		WebElement botaoChato = driver.findElement(By.id("onetrust-accept-btn-handler"));
//		botaoChato.click();
//		Thread.sleep(2000);
//		
//		//wait window update
//		wait.until(ExpectedConditions.titleContains("Investing.com Brasil"));
//		assertEquals("Investing.com Brasil - Finanças, Câmbio e Bolsa de Valores", driver.getTitle());
//	}
	
//	@Test
//	public void testGoogleSearch() throws InterruptedException {
//		driver.get("https://www.google.com/");
//		
//		assertEquals("Google", driver.getTitle());
//		System.out.println(">" + driver.getTitle());
//		
////		WebElement areaText = driver.findElement(By.cssSelector("input.gLFyf.gsfi"));
//		WebElement areaText = driver.findElement(By.className("gLFyf")); //not necessary gsfi
//		areaText.sendKeys("ifp");
//		Thread.sleep(2000);
//		areaText.clear();
//		Thread.sleep(2000);
//		areaText.sendKeys("IFPB");
//		Thread.sleep(1000);
//		
//		//press enter
//		areaText.sendKeys(Keys.ENTER);
//		//or click on search button
////		WebElement searchButton = driver.findElement(By.className("gNO89b"));
////		System.out.println(searchButton.getText());
////		searchButton.click();
//		
//		
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"rso\"]/div[1]/div/div/div/div/div/div/div[1]/a")));
//		//click on ifpb site
//		WebElement ifpbLink = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[1]/div/div/div/div/div/div/div[1]/a"));
//		ifpbLink.click();
//		 
//		
//		Thread.sleep(1000);
//		
//		assertEquals("https://www.ifpb.edu.br/", driver.getCurrentUrl());
//
//		driver.navigate().back();
//		Thread.sleep(1000);
//	}
	
//	@Test
//	public void testGoogleSearch() throws InterruptedException {
//		driver.get("https://www.ifpb.edu.br/");
//		
//		//click on monteiro link
//		WebElement monteiroLink = driver.findElement(By.xpath("//*[@id=\"portletwrapper-706c6f6e652e6c656674636f6c756d6e0a636f6e746578740a2f696670620a63616d7069\"]/dl/dd/ul/li[13]/a"));
//		monteiroLink.click();
//		
//		
//		Thread.sleep(2000);
//		//click on Monteiro description
//		driver.findElement(By.className("navTreeItem")).click();
//		
//		Thread.sleep(200000);
//		
//		WebElement dir = driver.findElement(By.xpath("//*[@id=\"parent-fieldname-text\"]/p[9]"));
//		assertTrue(dir.getText().contains("Abraão Romão Batista"));
//		
//		Thread.sleep(1000);
//	}
	

//	@Test
//	public void testInvestingListWebElement() throws InterruptedException {
//		driver.get("https://br.investing.com/");
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-accept-btn-handler")));
////		Thread.sleep(2000);
//		
//		
//		WebElement botaoChato = driver.findElement(By.id("onetrust-accept-btn-handler"));
//		botaoChato.click();
//		Thread.sleep(2000);
//		
//		WebElement areaBusca = driver.findElement(By.className("searchText"));
//		areaBusca.sendKeys("PETR4");
//		Thread.sleep(2000);//less than 2 sec can change the results
//		
//		List<WebElement> listaAcoes = driver.findElements(By.className("js-table-results"));
//		WebElement petro = listaAcoes.get(0);
//		String nomePetro = petro.findElement(By.className("second")).getText();
//		String nomeCompletoPetro = petro.findElement(By.className("third")).getText();
//		Thread.sleep(3000);
//		
//		
//		assertEquals("PETR4", nomePetro);
//		assertEquals("PETROBRAS Petroleo Brasileiro SA PN", nomeCompletoPetro);
//	}
	

	
	
//-----------------------------------------------------------
	
	
	
	
	
//	@Test
//	public void testInvesting() throws InterruptedException {
//		driver.get("https://br.investing.com/");
//		Thread.sleep(2000);
//		
//		WebElement botaoChato = driver.findElement(By.id("onetrust-accept-btn-handler"));
//		botaoChato.click();
//		
//		Thread.sleep(2000);
//		WebElement areaBusca = driver.findElement(By.className("searchText"));
//		areaBusca.sendKeys("Petr4");
////		areaBusca.submit();
////		System.out.println(areaBusca);
//		Thread.sleep(2000);
//		
////		WebElement botaoLupa = driver.findElement(By.className("searchGlassIcon"));
////		botaoLupa.click();
////		Thread.sleep(2000);
//		
//		List<WebElement> listaResultados = driver.findElements(By.className("js-table-results"));
//		WebElement petro = listaResultados.get(0);
//		WebElement name = petro.findElement(By.className("second"));
//		assertEquals(name.getText(), "PETR4");
//		Thread.sleep(3000);
//
//	}
	


}
