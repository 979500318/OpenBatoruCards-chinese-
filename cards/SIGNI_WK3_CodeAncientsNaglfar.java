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
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_WK3_CodeAncientsNaglfar extends Card {

    public SIGNI_WK3_CodeAncientsNaglfar()
    {
        setImageSets("WX24-P4-051");

        setOriginalName("コードアンシエンツ　ナグルファル");
        setAltNames("コードアンシエンツナグルファル Koodo Anshientsu Nagurufaru");
        setDescription("jp",
                "@C $TP：あなたのシグニのパワーを＋2000する。\n" +
                "@U：あなたのメインフェイズ以外でこのシグニが場を離れたとき、あなたのトラッシュからシグニ１枚を対象とし、あなたのエナゾーンからそれと同じレベルのシグニ１枚をトラッシュに置いてもよい。そうした場合、それを手札に加える。"
        );

        setName("en", "Code Ancients Naglfar");
        setDescription("en",
                "@C $TP: All of your SIGNI get +2000 power.\n" +
                "@U: When this SIGNI leaves the field other than during your main phase, target 1 SIGNI from your trash, and you may put 1 SIGNI with the same level from your ener zone into the trash. If you do, add it to your hand."
        );

		setName("zh_simplified", "古神代号 纳吉尔法");
        setDescription("zh_simplified", 
                "@C $TP :你的精灵的力量+2000。\n" +
                "@U :当在你的主要阶段以外把这只精灵离场时，从你的废弃区把精灵1张作为对象，可以从你的能量区把与其相同等级的精灵1张放置到废弃区。这样做的场合，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI(), new PowerModifier(2000));
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond()
        {
            return (!isOwnTurn() || getCurrentPhase() != GamePhase.MAIN) && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            
            if(target != null)
            {
                int level = target.getIndexedInstance().getLevelByRef();
                
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().fromEner().withLevel(level)).get();
                if(trash(cardIndex))
                {
                    addToHand(target);
                }
            }
        }
    }
}
