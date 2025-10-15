package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.AbilityCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.ModifiableValueModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityGuard;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class SIGNI_W3_SasheDissonaNaturalStarPrincess extends Card {

    public SIGNI_W3_SasheDissonaNaturalStarPrincess()
    {
        setImageSets("WXDi-P16-047", "WXDi-P16-047P");

        setOriginalName("羅星姫　サシェ//ディソナ");
        setAltNames("ラセイキサシェディソナ Raseiki Sashe Disona");
        setDescription("jp",
                "@C：対戦相手は%Xを支払わないかぎり、このシグニの正面にあるシグニでアタックできない。\n" +
                "@U：あなたのアタックフェイズ開始時、%W %X %Xを支払ってもよい。そうした場合、次の対戦相手のメインフェイズ終了時まで、対戦相手のセンタールリグのリミットを－１する。" +
                "~#：カードを２枚引く。このターン、あなたの手札にあるシグニは#Gを得る。"
        );

        setName("en", "Sashe//Dissona, Galactic Queen");
        setDescription("en",
                "@C: Your opponent cannot attack with the SIGNI in front of this SIGNI unless they pay %X.\n@U: At the beginning of your attack phase, you may pay %W %X %X. If you do, decrease the limit of your opponent's Center LRIG by one until the end of your opponent's next main phase." +
                "~#Draw two cards. The SIGNI in your hand gain a #G this turn."
        );
        
        setName("en_fan", "Sashe//Dissona, Natural Star Princess");
        setDescription("en_fan",
                "@C: The opponent's SIGNI in front of this SIGNI can't attack, unless your opponent pays %X.\n" +
                "@U: At the beginning of your attack phase, you may pay %W %X %X. If you do, until the end of your opponent's next main phase, your opponent's center LRIG gets --1 limit." +
                "~#Draw 2 cards. This turn, all SIGNI in your hand gain #G @[Guard]@."
        );

		setName("zh_simplified", "罗星姬 莎榭//失调");
        setDescription("zh_simplified", 
                "@C 对战对手如果不把%X:支付，那么这只精灵的正面的精灵不能攻击。\n" +
                "@U :你的攻击阶段开始时，可以支付%W%X %X。这样做的场合，直到下一个对战对手的主要阶段结束时为止，对战对手的核心分身的界限-1。" +
                "~#抽2张牌。这个回合，你的手牌的精灵得到#G。（持有#G的精灵得到[[防御]]）\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new TargetFilter().OP().SIGNI().opposite(), new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK, this::onConstEffModGetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private AbilityCost onConstEffModGetSample(RuleCheckData data)
        {
            return new EnerCost(Cost.colorless(1));
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(payEner(Cost.color(CardColor.WHITE, 1) + Cost.colorless(2)))
            {
                ChronoRecord record = new ChronoRecord(getLRIG(getOpponent()), ChronoDuration.nextPhaseEnd(getOpponent(), GamePhase.MAIN));
                
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().LRIG().match(getLRIG(getOpponent())),
                    new ModifiableValueModifier<>(this::onAttachedConstEffModGetSample, () -> -1d)
                );
                GFX.attachToAbility(attachedConst, new GFXZoneUnderIndicator(getOpponent(),CardLocation.LRIG, "limit_down"));
                attachPlayerAbility(getOwner(), attachedConst, record);
            }
        }
        private ModifiableDouble onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getLimit();
        }

        private void onLifeBurstEff()
        {
            draw(2);

            ConstantAbilityShared attachedConstShared = new ConstantAbilityShared(new TargetFilter().own().SIGNI().fromHand(), new AbilityGainModifier(this::onAttachedConstEff2ModGetSample));
            attachPlayerAbility(getOwner(), attachedConstShared, ChronoDuration.turnEnd());
        }
        private Ability onAttachedConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityGuard());
        }
    }
}
