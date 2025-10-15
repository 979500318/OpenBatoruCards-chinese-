package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_R2_TawilColors extends Card {
    
    public LRIGA_R2_TawilColors()
    {
        setImageSets("WXDi-D01-007");
        
        setOriginalName("タウィル＝カラーズ");
        setAltNames("タウィルカラーズ Tauiru Karaazu");
        setDescription("jp",
                "@E：あなたの場に赤のシグニがある場合、対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。青のシグニがある場合、カードを２枚引く。緑のシグニがある場合、[[エナチャージ２]]する。"
        );
        
        setName("en", "Tawil =Rainbow=");
        setDescription("en",
                "@E: If there is a red SIGNI on your field, vanish target SIGNI on your opponent's field with power 10000 or less. If there is a blue SIGNI on your field, draw two cards. If there is a green SIGNI on your field, [[Ener Charge 2]]."
        );
        
        setName("en_fan", "Tawil-Colors");
        setDescription("en_fan",
                "@E: If there is a red SIGNI on your field, target 1 of your opponent's SIGNI with power 10000 or less, and banish it. If there is a blue SIGNI, draw 2 cards. If there is a green SIGNI, [[Ener Charge 2]]."
        );
        
		setName("zh_simplified", "塔维尔=幻彩");
        setDescription("zh_simplified", 
                "@E :你的场上有红色的精灵的场合，对战对手的力量10000以下的精灵1只作为对象，将其破坏。有蓝色的精灵的场合，抽2张牌。有绿色的精灵的场合，[[能量填充2]]。（从你的牌组上面把2张牌放置到能量区）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TAWIL);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.RED);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);
        
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
            if(new TargetFilter().own().SIGNI().withColor(CardColor.RED).getValidTargetsCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
                banish(cardIndex);
            }
            if(new TargetFilter().own().SIGNI().withColor(CardColor.BLUE).getValidTargetsCount() > 0)
            {
                draw(2);
            }
            if(new TargetFilter().own().SIGNI().withColor(CardColor.GREEN).getValidTargetsCount() > 0)
            {
                enerCharge(2);
            }
        }
    }
}
