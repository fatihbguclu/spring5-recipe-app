package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository repository;

    public ImageServiceImpl(RecipeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void saveImageFile(Long recipeId, MultipartFile multipartFile) {
        try {
            Recipe recipe = repository.findById(recipeId).get();

            Byte[] byteObjects = new Byte[multipartFile.getBytes().length];

            int i = 0;

            for (byte b : multipartFile.getBytes()){
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);

            repository.save(recipe);
        } catch (IOException e) {
            //todo handle better
            log.error("Error occurred", e);

            e.printStackTrace();
        }
    }
}
