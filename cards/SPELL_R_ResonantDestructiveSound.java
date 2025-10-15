package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class SPELL_R_ResonantDestructiveSound extends Card {
    
    public SPELL_R_ResonantDestructiveSound()
    {
        setImageSets("WXDi-P01-060");
        
        setOriginalName("共鳴の壊音");
        setAltNames("キョウメイノカイオン Kyoumei no Kaion");
        setDescription("jp",
                "対戦相手のパワー12000以下のすべてのシグニをバニッシュする。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Resonating Sound of Destruction");
        setDescription("en",
                "Vanish all SIGNI on your opponent's field with power 12000 or less." +
                "~#Vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Resonant Destructive Sound");
        setDescription("en_fan",
                "Banish all of your opponent's SIGNI with power 12000 or less." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );
        
		setName("zh_simplified", "共鸣的坏音");
        setDescription("zh_simplified", 
                "对战对手的力量12000以下的全部的精灵破坏。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 6));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerSpellAbility(this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            banish(new TargetFilter().OP().SIGNI().withPower(0,12000).getExportedData());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(cardIndex);
        }
    }
}
