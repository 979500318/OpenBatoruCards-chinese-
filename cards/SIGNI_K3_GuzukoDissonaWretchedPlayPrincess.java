package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventTarget;

public final class SIGNI_K3_GuzukoDissonaWretchedPlayPrincess extends Card {

    public SIGNI_K3_GuzukoDissonaWretchedPlayPrincess()
    {
        setImageSets("WXDi-P13-054", "WXDi-P13-054P");

        setOriginalName("惨之遊姫　グズ子//ディソナ");
        setAltNames("サンノユウキグズコディソナ San no Yuuki Guzuko Disona");
        setDescription("jp",
                "@U $T1：このシグニが対戦相手の、能力か効果の対象になったとき、あなたのデッキの一番上のカードをトラッシュに置く。そのカードがシグニの場合、%K %Xを支払ってもよい。そうした場合、そのカードをダウン状態で場に出す。\n" +
                "@E %K %X %X：対戦相手のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Guzuko//Dissona, Tragic Party Queen");
        setDescription("en",
                "@U $T1: When this SIGNI becomes the target of an ability or effect of your opponent, put the top card of your deck into your trash. If that card is a SIGNI, you may pay %K %X. If you do, put that card from your trash onto your field downed.\n@E %K %X %X: Vanish target SIGNI on your opponent's field." +
                "~#Add target SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Guzuko//Dissona, Wretched Play Princess");
        setDescription("en_fan",
                "@U $T1: When this SIGNI is targeted by your opponent's ability or effect, put the top card of your deck into the trash. If that card is a SIGNI, you may pay %K %X. If you do, put it onto the field downed.\n" +
                "@E %K %X %X: Target 1 of your opponent's SIGNI, and banish it." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "惨之游姬 迟钝子//失调");
        setDescription("zh_simplified", 
                "@U $T1 :当这只精灵被作为对战对手的，能力或效果的对象时，你的牌组最上面的牌放置到废弃区。那张牌是精灵的场合，可以支付%K%X。这样做的场合，那张牌以横置状态出场。\n" +
                "@E %K%X %X:对战对手的精灵1只作为对象，将其破坏。" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.DISSONA | CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.TARGET, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(2)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                   EventTarget.getDataSourceTargetRole() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex cardIndex = millDeck(1).get();
            
            if(cardIndex != null && CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) && cardIndex.getIndexedInstance().isPlayable() &&
               payEner(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)))
            {
                putOnField(cardIndex, Enter.DOWNED);
            }
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
            if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
            {
                addToHand(target);
            } else {
                putOnField(target);
            }
        }
    }
}

