package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_Code2434SangoSuo extends Card {

    public SIGNI_K3_Code2434SangoSuo()
    {
        setImageSets("WXDi-CP01-031");

        setOriginalName("コード２４３４　周央サンゴ");
        setAltNames("コードニジサンジスオウサンゴ Koodo Nijisanji Suou Sango");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場とエナゾーンに＜世怜音女学院＞のシグニが合計５種類ある場合、次の対戦相手のターン終了時まで、このシグニの基本パワーは35000になる。\n" +
                "@E %K：あなたのデッキの上からカードを３枚トラッシュに置く。その後、あなたのトラッシュから＜バーチャル＞のシグニ１枚を対象とし、それを場に出す。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Suo Sango, Code 2434");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are exactly five different <<SELENE Girls' Academy>> SIGNI on your field and in your Ener Zone in total, the base power of this SIGNI becomes 35000 until the end of your opponent's next end phase.\n@E %K: Put the top three cards of your deck into your trash. Then, put target <<Virtual>> SIGNI from your trash onto your field." +
                "~#Add target SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Code 2434 Sango Suo");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are a total of 5 types of <<Selene Girls' Academy>> SIGNI on your field and in your ener zone, until the end of your opponent's next turn, this SIGNI's base power becomes 35000.\n" +
                "@E %K: Put the top 3 cards of your deck into the trash. Then, target 1 <<Virtual>> SIGNI from your trash, and put it onto the field." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "2434代号 周央珊瑚");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上和能量区的<<世怜音女学院>>精灵在合计5种类的场合，直到下一个对战对手的回合结束时为止，这只精灵的基本力量变为35000。\n" +
                "@E %K:从你的牌组上面把3张牌放置到废弃区。然后，从你的废弃区把<<バーチャル>>精灵1张作为对象，将其出场。" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL, CardSIGNIClass.SELENE_GIRLS_ACADEMY);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().withClass(CardSIGNIClass.SELENE_GIRLS_ACADEMY).or(new TargetFilter().SIGNI(), new TargetFilter().SIGNI().fromEner()).getExportedData().
                stream().map(c -> ((CardIndex)c).getCardReference().getOriginalName()).distinct().count() == 5)
            {
                setBasePower(getCardIndex(), 35000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }

        private void onEnterEff()
        {
            millDeck(3);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash().playable()).get();
            putOnField(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
