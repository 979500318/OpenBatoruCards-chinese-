package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_CodeMazeRedBrick extends Card {

    public SIGNI_R2_CodeMazeRedBrick()
    {
        setImageSets("WX24-P2-072");

        setOriginalName("コードメイズ　アカレンガ");
        setAltNames("コードメイズアカレンガ Koodo Meizu Aka Renga");
        setDescription("jp",
                "@U $TO $T1: 対戦相手のシグニ１体がこのシグニの正面に配置されたとき、そのシグニのパワーが3000以下の場合、そのシグニをバニッシュする。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Code Maze Red Brick");
        setDescription("en",
                "@U $TO $T1: When 1 of your opponent's SIGNI is placed in front of this SIGNI, if that SIGNI's power is 3000 or less, banish it." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "迷宫代号 横滨红砖仓库");
        setDescription("zh_simplified", 
                "@U $TO $T1 :当对战对手的精灵1只在这只精灵的正面配置时，那只精灵的力量在3000以下的场合，那只精灵破坏。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PLACE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && !isOwnCard(caller) && caller.getLocation() == CardLocation.getOppositeSIGNILocation(getCardIndex().getLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(caller.getIndexedInstance().getPower().getValue() <= 3000)
            {
                banish(caller);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
