package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityCantAttack;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class LRIGA_R2_CarnivalSin extends Card {

    public LRIGA_R2_CarnivalSin()
    {
        setImageSets("WXDi-P11-032");

        setOriginalName("カーニバル　－罪－");
        setAltNames("カーニバルザイ Kaanibaru Zai");
        setDescription("jp",
                "@E：シグニ１体を対象とし、ターン終了時まで、それは【アサシン】か【ランサー】か@>@C：アタックできない。@@を得る。\n" +
                "@E %X %X：シグニ１体を対象とし、ターン終了時まで、それは【アサシン】か【ランサー】か@>@C：アタックできない。@@を得る。"
        );

        setName("en", "Carnival -Sin-");
        setDescription("en",
                "@E: Target SIGNI gains [[Assassin]], [[Lancer]], or @>@C: This SIGNI cannot attack.@@until end of turn.\n" +
                "@E %X %X: Target SIGNI gains [[Assassin]], [[Lancer]], or @>@C: This SIGNI cannot attack.@@until end of turn."
        );
        
        setName("en_fan", "Carnival -Sin-");
        setDescription("en_fan",
                "@E: Target 1 SIGNI, and until end of turn, it gains [[Assassin]], or [[Lancer]], or:" +
                "@>@C: Can't attack.@@" +
                "@E %X %X: Target 1 SIGNI, and until end of turn, it gains [[Assassin]], or [[Lancer]], or:" +
                "@>@C: Can't attack."
        );

		setName("zh_simplified", "嘉年华 -罪-");
        setDescription("zh_simplified", 
                "@E :精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]或[[枪兵]]或\n" +
                "@>@C :不能攻击。@@\n" +
                "@E %X %X:精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]或[[枪兵]]或\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.CARNIVAL);
        setColor(CardColor.RED);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

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
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).SIGNI()).get();
            
            if(target != null)
            {
                attachAbility(target, switch(playerChoiceAction(ActionHint.ASSASSIN, ActionHint.LANCER, ActionHint.CANTATK))
                {
                    case 1 -> new StockAbilityAssassin();
                    case 2 -> new StockAbilityLancer();
                    case 3 -> new StockAbilityCantAttack();
                    default -> null;
                }, ChronoDuration.turnEnd());
            }
        }
    }
}
