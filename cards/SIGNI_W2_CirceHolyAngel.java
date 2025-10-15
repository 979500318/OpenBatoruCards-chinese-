package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W2_CirceHolyAngel extends Card {
    
    public SIGNI_W2_CirceHolyAngel()
    {
        setImageSets("WXDi-P02-050");
        
        setOriginalName("聖天　キルケー");
        setAltNames("セイテンキルケー Seiten Kirukee");
        setDescription("jp",
                "~#：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。カードを１枚引く。"
        );
        
        setName("en", "Circe, Blessed Angel");
        setDescription("en",
                "~#Add target SIGNI with a #G from your trash to your hand. Draw a card."
        );
        
        setName("en_fan", "Circe, Holy Angel");
        setDescription("en_fan",
                "~#Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand. Draw 1 card."
        );
        
		setName("zh_simplified", "圣天 喀耳刻");
        setDescription("zh_simplified", 
                "~#从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
            
            draw(1);
        }
    }
}
