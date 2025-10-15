package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B1_VinylPoolFirstPlay extends Card {

    public SIGNI_B1_VinylPoolFirstPlay()
    {
        setImageSets("WX24-P4-070");

        setOriginalName("壱ノ遊　ビニールプール");
        setAltNames("イチノユウビニールプール Ichi no Yuu Biniiru Puuru");
        setDescription("jp",
                "@U：あなたのアタックフェイズの間、このシグニが場を離れたとき、あなたの手札からレベル１の＜遊具＞のシグニ１枚をダウン状態で場に出してもよい。そのシグニの@E能力は発動しない。"
        );

        setName("en", "Vinyl Pool, First Play");
        setDescription("en",
                "@U: During your attack phase, when this SIGNI leaves the field, you may put 1 level 1 <<Playground Equipment>> SIGNI from your hand onto the field downed. That SIGNI's @E abilities don't activate."
        );

		setName("zh_simplified", "壹之游 嬉水池");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段期间，当这只精灵离场时，可以从你的手牌把等级1的<<遊具>>精灵1张以#D状态出场。那只精灵的@E能力不能发动。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(1);
        setPower(3000);

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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && GamePhase.isAttackPhase(getCurrentPhase()) && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).withLevel(1).fromHand().playable()).get();
            putOnField(target, Enter.DOWNED | Enter.DONT_ACTIVATE);
        }
    }
}
