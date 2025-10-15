package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_K3_HadesWickedDevilPrincess extends Card {

    public SIGNI_K3_HadesWickedDevilPrincess()
    {
        setImageSets("WX24-P2-058");

        setOriginalName("凶魔姫　ハデス");
        setAltNames("キョウマキハデス Kyoumaki Hadesu");
        setDescription("jp",
                "@U：このシグニがコストか効果によって場を離れたとき、あなたのデッキの上からカードを３枚トラッシュに置く。その後、この方法でトラッシュに置かれたカードの中からシグニを１枚まで対象とし、以下の２つから１つを選ぶ。\n" +
                "$$1それを手札に加える。\n" +
                "$$2それのレベル１につき%Xを支払ってもよい。そうした場合、それをダウン状態で場に出す。それの@E能力は発動しない。" +
                "~#：あなたのトラッシュから##を持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Hades, Wicked Devil Princess");
        setDescription("en",
                "@U: When this SIGNI leaves the field due to a cost or an effect, put the top 3 cards of your deck into the trash. Then, target up to 1 card that was put into the trash this way, and @[@|choose 1 of the following:|@]@\n" +
                "$$1 Add it to your hand.\n" +
                "$$2 You may pay %X for each of its levels. If you do, put it onto the field downed. Its @E abilities don't activate." +
                "~#Target 1 of your SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "凶魔姬 哈迪斯");
        setDescription("zh_simplified", 
                "@U :当这只精灵因为费用或效果离场时，从你的牌组上面把3张牌放置到废弃区。然后，从这个方法放置到废弃区的牌中把精灵1张最多作为对象，从以下的2种选1种。\n" +
                "$$1 将其加入手牌。\n" +
                "$$2 可以依据其的等级的数量，每有1级就把%X支付。这样做的场合，将其以#D状态出场，其的@E能力不能发动。" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) && getEvent().getSourceAbility() != null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            DataTable<CardIndex> data = millDeck(3);

            CardIndex target = playerTargetCard(0,1, new TargetFilter().fromTrash().match(data)).get();

            if(target != null)
            {
                if(playerChoiceMode() == 1)
                {
                    addToHand(target);
                } else if(CardType.isSIGNI(target.getCardReference().getType()) && target.getIndexedInstance().isPlayable() &&
                          payEner(Cost.colorless(target.getIndexedInstance().getLevelByRef())))
                {
                    putOnField(target, Enter.DOWNED | Enter.DONT_ACTIVATE);
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();

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
