package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class LRIGA_R2_HiranaGrowin extends Card {
    
    public LRIGA_R2_HiranaGrowin()
    {
        setImageSets("WXDi-P02-011");
        
        setOriginalName("ヒラナ＊グローイン");
        setAltNames("ヒラナグローイン Hirana Gurooin");
        setDescription("jp",
                "@E：カードを１枚引くか[[エナチャージ１]]をする。\n" +
                "@E：対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "Hirana*Glowing");
        setDescription("en",
                "@E: Draw a card or [[Ener Charge 1]].\n" +
                "@E: Put target card from your opponent's Ener Zone that does not share a color with your opponent's center LRIG into their trash."
        );
        
        setName("en_fan", "Hirana*Growin'");
        setDescription("en_fan",
                "@E: Draw 1 card or [[Ener Charge 1]].\n" +
                "@E: Target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash."
        );
        
		setName("zh_simplified", "平和＊成长中");
        setDescription("zh_simplified", 
                "@E :抽1张牌或[[能量填充1]]。\n" +
                "@E :从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.RED);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(1);
            } else {
                enerCharge(1);
            }
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor()))).get();
            trash(target);
        }
    }
}
