package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class ARTS_W_ShiningClock extends Card {

    public ARTS_W_ShiningClock()
    {
        setImageSets("WX24-P1-002", "WX24-P1-002U");

        setOriginalName("シャイニング・クロック");
        setAltNames("シャイニングクロック Shainingu Kurokku");
        setDescription("jp",
                "対戦相手のルリグかシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "&E４枚以上@0追加で対戦相手のシグニを１体まで対象とし、ターン終了時まで、それは能力を失う。"
        );

        setName("en", "Shining Clock");
        setDescription("en",
                "Target 1 of your opponent's LRIG or SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "&E4 or more@0 Additionally, target up to 1 of your opponent's SIGNI, and until end of turn, it loses its abilities."
        );

		setName("zh_simplified", "闪耀·时光");
        setDescription("zh_simplified", 
                "对战对手的分身或精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "&E4张以上@0追加对战对手的精灵1只最多作为对象，直到回合结束时为止，其的能力失去。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerARTSAbility(this::onARTSEff).setRecollect(4);
        }
        
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().fromField()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            
            if(getAbility().isRecollectFulfilled())
            {
                target = playerTargetCard(0,1, new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
                if(target != null) disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }
    }
}

