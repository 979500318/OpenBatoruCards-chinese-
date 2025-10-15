package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_KanaloaAzureAngel extends Card {
    
    public SIGNI_B2_KanaloaAzureAngel()
    {
        setImageSets("WXDi-P08-064");
        
        setOriginalName("蒼天　カナロア");
        setAltNames("ソウテンカナロア Souten Kanaroa");
        setDescription("jp",
                "@E：あなたの場にいる青のルリグ１体につき対戦相手のシグニを１体まで対象とし、それらを凍結する。"
        );
        
        setName("en", "Kanaloa, Azure Angel");
        setDescription("en",
                "@E: Freeze up to one target SIGNI on your opponent's field for every blue LRIG on your field."
        );
        
        setName("en_fan", "Kanaloa, Azure Angel");
        setDescription("en_fan",
                "@E: For each blue LRIG on your field, target 1 of your opponent's SIGNI, and freeze it."
        );
        
		setName("zh_simplified", "苍天 卡纳洛阿");
        setDescription("zh_simplified", 
                "@E :依据你的场上的蓝色的分身的数量，每有1只就把对战对手的精灵1只最多作为对象，将这些冻结。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            int count = new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount();
            
            for(int i=0;i<count;i++)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
                freeze(target);
            }
        }
    }
}
