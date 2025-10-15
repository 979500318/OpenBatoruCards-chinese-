package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class LRIGA_W2_SasheSnake extends Card {

    public LRIGA_W2_SasheSnake()
    {
        setImageSets("WXDi-P13-031");

        setOriginalName("サシェ・スネイク");
        setAltNames("サシェスネイク Sashe Suneiku");
        setDescription("jp",
                "@E：ターン終了時まで、このルリグは@>@C：対戦相手は%X %X %Xを支払わないかぎりシグニでアタックできない。@@を得る。"
        );

        setName("en", "Sashe Snake");
        setDescription("en",
                "@E: This LRIG gains@>@C: Your opponent cannot attack with SIGNI unless they pay %X %X %X.@@until end of turn. "
        );
        
        setName("en_fan", "Sashe Snake");
        setDescription("en_fan",
                "@E: Until end of turn, this LRIG gains:" +
                "@>@C: Your opponent's SIGNI can't attack unless your opponent pays %X %X %X."
        );

		setName("zh_simplified", "莎榭·蛇舞");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，这只分身得到\n" +
                "@>@C 对战对手如果不把%X %X %X:支付，那么精灵不能攻击。@@\n" +
                "。（每次攻击都要支付）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.SASHE);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(5));
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
        }

        private void onEnterEff()
        {
            ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(),
                new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK, data -> new EnerCost(Cost.colorless(3)))
            );
            
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
        }
    }
}
