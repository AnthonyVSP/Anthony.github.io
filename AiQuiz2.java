package MeuPerfil;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

@DisplayName("Testes automatizados da funcionalidade Meu Perfil")
public class AiQuiz2 {

    @Test
    @DisplayName("Registrar nova resposta")
    public void testEditarPerfil() throws InterruptedException, IOException {
        //Abrir o navegador
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();
        navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

        //Entrar no AiQuiz
        navegador.get("http://200.132.136.72/AIQuiz2/");

        //Deixa o navegador em tela cheia
        navegador.manage().window().maximize();

        //Fazer Login
        navegador.findElement(By.name("login")).sendKeys("grupo3");
        navegador.findElement(By.name("password")).sendKeys("A#abc123");
        Thread.sleep(1000);
        navegador.findElement(By.name("btn_entrar")).click();

        // Carregar o JSON do arquivo\
        String data = new String(Files.readAllBytes(Paths.get("src/test/java/MeuPerfil/MeuPerfil.json")));
        JSONArray jsonArray = new JSONArray(data);

        //Entrar em responder online
        Thread.sleep(1000);
        navegador.findElement(By.cssSelector("#side-menu a[href='index.php?class=QuestionarioList'")).click();

        // Clique no Administrador
        WebElement userMenu = navegador.findElement(By.xpath("//span[contains(text(),'Administrator (grupo3)')]"));

        // Clicando no botão
        userMenu.click();

        // Clique na ação : Perfil
        navegador.findElement(By.cssSelector("a[href*='index.php?class=SystemProfileView&adianti_open_tab=1&adianti_tab_name=Perfil']")).click();

        // Clique na ação : Editar
        navegador.findElement(By.cssSelector("a[href*='index.php?class=SystemProfileForm&method=onEdit']")).click();

        for (int i = 0; i < 16; i++) {
            // int i irá percorrer o array de objetos json
            JSONObject infoPerfilCorreto = jsonArray.getJSONObject(i);

            // Verificar se os campos usuario e senha existem e não são nulos
            String enderecoCorreto = infoPerfilCorreto.optString("endereco", null);
            String telefoneCorreto = infoPerfilCorreto.optString("telefone", null);
            String sobreCorreto = infoPerfilCorreto.optString("sobre", null);
            String funcaoCorreto = infoPerfilCorreto.optString("funcao", null);
            String comportamentoEsperado = infoPerfilCorreto.optString("comportamentoEsperado", "sucesso");

            WebElement PerfilElement = navegador.findElement(By.name("address"));
            // Preencher campos de endereço, telefone, sobre e função, se não forem nulos
            if (enderecoCorreto != null) {
                Thread.sleep(1000);
                PerfilElement.sendKeys(enderecoCorreto);
                WebElement PerfilElement2 = navegador.findElement(By.name("phone"));
                if (telefoneCorreto != null) {
                    Thread.sleep(1000);
                    PerfilElement2.sendKeys(telefoneCorreto);
                    WebElement PerfilElement3 = navegador.findElement(By.name("about"));
                    if (sobreCorreto != null) {
                        Thread.sleep(1000);
                        PerfilElement3.sendKeys(sobreCorreto);
                        WebElement PerfilElement4 = navegador.findElement(By.name("function_name"));
                        if (funcaoCorreto != null) {
                            Thread.sleep(1000);
                            PerfilElement4.sendKeys(funcaoCorreto);
                            if (comportamentoEsperado.equals("sucesso")){
                                int organizador;
                                organizador = i+1;
                                System.out.println("teste "  +  organizador  +  " Perfil editado com sucesso");
                            }else {
                                int organizador;
                                organizador = i+1;
                                System.out.println("teste "  +  organizador  +  " Erro ao editar Perfil");
                            }
                        }
                    }
                }
            }
            navegador.findElement(By.name("btn_salvar")).click();
            navegador.findElement(By.xpath("//button[text()='OK']")).click();
            limparCampos(navegador);
            }
        }
    public void limparCampos(WebDriver navegador){
        WebElement PerfilElement = navegador.findElement(By.name("address"));
        WebElement PerfilElement2 = navegador.findElement(By.name("phone"));
        WebElement PerfilElement3 = navegador.findElement(By.name("about"));
        WebElement PerfilElement4 = navegador.findElement(By.name("function_name"));
        PerfilElement.clear();
        PerfilElement2.clear();
        PerfilElement3.clear();
        PerfilElement4.clear();
    }
}