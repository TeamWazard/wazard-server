package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.applicatino.port.domain.LogoImage;
import shop.wazard.applicatino.port.out.SaveFileRepository;
import shop.wazard.entity.store.LogoImageJpa;

@Repository
@RequiredArgsConstructor
class FileDbAdapter implements SaveFileRepository {

    private final FileJpaRepository fileJpaRepository;
    private final FileMapper fileMapper;

    @Override
    public LogoImage uploadStoreLogo(LogoImage logoImage) {
        LogoImageJpa logoImageJpa = fileJpaRepository.save(fileMapper.toLogoImageJpa(logoImage));
        return fileMapper.toLogoImage(logoImageJpa);
    }

}
