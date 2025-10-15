package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_NeonTetraWaterPhantom extends Card {
    
    public SIGNI_B1_NeonTetraWaterPhantom()
    {
        setImageSets("WXDi-P00-060");
        
        setOriginalName("幻水　ネオンテトラ");
        setAltNames("ゲンスイネオンテトラ Gensui Neon Tetora");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それを凍結する。" +
                "~#対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );
        
        setName("en", "Neon Tetra, Phantom Aquatic Beast");
        setDescription("en",
                "@E: Freeze target SIGNI on your opponent's field." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Neon Tetra, Water Phantom");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and freeze it." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );
        
		setName("zh_simplified", "幻水 彩虹方头鱼");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其冻结。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(1);
        setPower(3000);
        
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(cardIndex);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(cardIndex);
            freeze(cardIndex);
            
            draw(1);
        }
    }
}
