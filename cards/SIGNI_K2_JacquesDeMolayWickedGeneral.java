package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_K2_JacquesDeMolayWickedGeneral extends Card {

    public SIGNI_K2_JacquesDeMolayWickedGeneral()
    {
        setImageSets("WXDi-P09-080");

        setOriginalName("凶将　ジャック・ド・モレー");
        setAltNames("キョウショウジャックドモレー Kyoushou Jakku do Moree Molai");
        setDescription("jp",
                "@U $T1：あなたのシグニ１体がトラッシュから場に出たとき、次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。\n" +
                "@A $T1 #C #C #C：あなたのトラッシュから黒のシグニ１枚を対象とし、それを場に出す。" +
                "~#：あなたのトラッシュから#Gを持たないレベル２以下のシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Jacques de Molay, Doomed General");
        setDescription("en",
                "@U $T1: When a SIGNI enters your field from the trash, this SIGNI gets +4000 power until the end of your opponent's next end phase.\n" +
                "@A $T1 #C #C #C: Put target black SIGNI from your trash onto your field." +
                "~#Add target level two or less SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Jacques de Molay, Wicked General");
        setDescription("en_fan",
                "@U $T1: When a SIGNI enters the field from your trash, until the end of your opponent's next turn, this SIGNI gets +4000 power.\n" +
                "@A $T1 #C #C #C: Target 1 black SIGNI from your trash, and put it onto the field." +
                "~#Target 1 level 2 or lower SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "凶将 雅克·德·莫莱");
        setDescription("zh_simplified", 
                "@U $T1 :当你的精灵1只从废弃区出场时，直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。\n" +
                "@A $T1 #C #C #C:从你的废弃区把黑色的精灵1张作为对象，将其出场。" +
                "~#从你的废弃区把不持有#G的等级2以下的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act = registerActionAbility(new CoinCost(3), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) &&
                   caller.getOldLocation() == CardLocation.TRASH ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.BLACK).fromTrash().playable()).get();
            putOnField(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().withLevel(0,2).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
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
