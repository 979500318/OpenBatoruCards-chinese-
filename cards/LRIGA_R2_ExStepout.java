package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class LRIGA_R2_ExStepout extends Card {
    
    public LRIGA_R2_ExStepout()
    {
        setImageSets("WXDi-P04-021");
        
        setOriginalName("エクスステップアウト");
        setAltNames("Ekusu Suteppu Auto");
        setDescription("jp",
                "@E：カードを２枚引くか[[エナチャージ２]]をする。\n" +
                "@E：あなたのシグニ１体を対象とし、ターン終了時まで、それは[[アサシン]]を得る。"
        );
        
        setName("en", "Ex Stepout");
        setDescription("en",
                "@E: Draw two cards or [[Ener Charge 2]].\n" +
                "@E: Target SIGNI on your field gains [[Assassin]] until end of turn."
        );
        
        setName("en_fan", "Ex Stepout");
        setDescription("en_fan",
                "@E: Draw 2 cards or [[Ener Charge 2]].\n" +
                "@E: Target 1 of your SIGNI, and until end of turn, it gains [[Assassin]]."
        );
        
		setName("zh_simplified", "艾克斯放逐步骤");
        setDescription("zh_simplified", 
                "@E :抽2张牌或[[能量填充2]]。\n" +
                "@E :你的精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.EX);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityAssassin(), ChronoDuration.turnEnd());
        }
    }
}
