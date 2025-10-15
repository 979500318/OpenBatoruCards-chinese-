package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class LRIGA_R2_HiranaPowerOn extends Card {
    
    public LRIGA_R2_HiranaPowerOn()
    {
        setImageSets("WXDi-P02-013");
        
        setOriginalName("ヒラナ＊パワーオン");
        setAltNames("ヒラナパワーオン Hirana Pawaaon");
        setDescription("jp",
                "@E：カードを２枚引くか[[エナチャージ２]]をする。\n" +
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Hirana*Power On");
        setDescription("en",
                "@E: Draw two cards or [[Ener Charge 2]].\n" +
                "@E: Vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Hirana*Power-On");
        setDescription("en_fan",
                "@E: Draw 2 cards or [[Ener Charge 2]].\n" +
                "@E: Target 1 of your opponent's SIGNI, and banish it."
        );
        
		setName("zh_simplified", "平和＊充电中");
        setDescription("zh_simplified", 
                "@E :抽2张牌或[[能量填充2]]。\n" +
                "@E :对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.RED);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
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
                draw(2);
            } else {
                enerCharge(2);
            }
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
    }
}
