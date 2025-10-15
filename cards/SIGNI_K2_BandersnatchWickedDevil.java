package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K2_BandersnatchWickedDevil extends Card {

    public SIGNI_K2_BandersnatchWickedDevil()
    {
        setImageSets("WX24-P4-088");

        setOriginalName("凶魔　バンダースナッチ");
        setAltNames("キョウマバンダースナッチ Kyouma Bandaasunacchi");
        setDescription("jp",
                "@U $TO $T1：あなたの効果によっていずれかのプレイヤーのデッキからカード１枚がトラッシュに置かれたとき、次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。" +
                "~#：あなたのトラッシュから＜悪魔＞のシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Bandersnatch, Wicked Devil");
        setDescription("en",
                "@U $TO $T1: When a card is put from a deck into the trash by your effect, until the end of your opponent's next turn, this SIGNI gets +4000 power." +
                "~#Target 1 <<Devil>> SIGNI from your trash, and add it to hand or put it onto the field."
        );

		setName("zh_simplified", "凶魔 潘达斯奈基");
        setDescription("zh_simplified", 
                "@U $TO $T1 :当因为你的效果从任一方的玩家的牌组把1张牌放置到废弃区时，直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。" +
                "~#从你的废弃区把<<悪魔>>精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && caller.isEffectivelyAtLocation(CardLocation.DECK_MAIN) &&
                    getEvent().getSourceAbility() != null && getEvent().getSourceCost() == null && isOwnCard(getEvent().getSource()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromTrash()).get();
            
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
