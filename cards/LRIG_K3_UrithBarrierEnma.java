package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.TrashCost;

public final class LRIG_K3_UrithBarrierEnma extends Card {

    public LRIG_K3_UrithBarrierEnma()
    {
        setImageSets("WX24-P1-015", "WX24-P1-015U");

        setOriginalName("挟界の閻魔　ウリス");
        setAltNames("キョウカイノエンマウリス Kyoukai no Enma Urisu");
        setDescription("jp",
                "@U $T1：あなたのメインフェイズの間、あなたのレベル２以下の＜悪魔＞のシグニ１体がコストか効果によって場からトラッシュに置かれたとき、あなたのトラッシュからそのシグニより高いレベルを持つ＜悪魔＞のシグニを１枚まで対象とし、それを場に出す。\n" +
                "@A $G1 @[@|デザイア|@]@ @[シグニ１体を場からトラッシュに置く]@：あなたのトラッシュから##を持たないカード１枚を対象とし、それをライフクロスに加える。"
        );

        setName("en", "Urith, Barrier Enma");
        setDescription("en",
                "@U $T1: During your main phase, when 1 of your level 2 or lower <<Devil>> SIGNI is put from your field into the trash due to a cost or an effect, target up to 1 <<Devil>> SIGNI with a higher level than that SIGNI from your trash, and put it onto the field.\n" +
                "@A @[@|Desire|@]@ $G1 @[Put 1 SIGNI from your field into the trash]@: Target 1 card without ## @[Life Burst]@ from your trash, and add it to life cloth."
        );

		setName("zh_simplified", "挟界的阎魔 乌莉丝");
        setDescription("zh_simplified", 
                "@U $T1 :你的主要阶段期间，当你的等级2以下的<<悪魔>>精灵1只因为费用或效果从场上放置到废弃区时，从你的废弃区把持有比那只精灵的等级高的<<悪魔>>精灵1张最多作为对象，将其出场。\n" +
                "@A $G1 野望:精灵1只从场上放置到废弃区从你的废弃区把不持有##的牌1张作为对象，将其加入生命护甲。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

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
            auto.enableEventSourceSelection();

            ActionAbility act = registerActionAbility(new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.DEVIL)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Desire");
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN &&
                   isOwnCard(caller) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.DEVIL) && CardLocation.isSIGNI(caller.getLocation()) &&
                   caller.getIndexedInstance().getLevel().getValue() <= 2 && getEvent().getSourceAbility() != null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.DEVIL).withLevel(getEvent().getCaller().getLevel().getValue()+1,0).fromTrash()).get();
            putOnField(target);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HEAL).own().not(new TargetFilter().lifeBurst()).fromTrash()).get();
            addToLifeCloth(target);
        }
    }
}
