package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K3_CodeLabyrintheLouvre extends Card {

    public SIGNI_K3_CodeLabyrintheLouvre()
    {
        setImageSets("WX24-P2-057");
        setLinkedImageSets("WX24-P2-030");

        setOriginalName("コードラビラント　ルーブル");
        setAltNames("コードラビラントルーブル Koodo Rabiranto Ruubaru");
        setDescription("jp",
                "@C：アタックフェイズの間、あなたの場に《エニグマ/メイデン　イオナ》がいるかぎり、このシグニの正面のシグニのパワーを－3000する。\n" +
                "@C：あなたのアタックフェイズの間、あなたの場に他の＜迷宮＞のシグニがあるかぎり、このシグニの正面のシグニのパワーを－4000する。\n" +
                "@E：対戦相手の場にあるすべてのシグニを好きなように配置し直す。"
        );

        setName("en", "Code Labyrinthe Louvre");
        setDescription("en",
                "@C: During the attack phase, as long as your LRIG is \"Iona, Enigma/Maiden\", the SIGNI in front of this SIGNI gets --3000 power.\n" +
                "@C: During your attack phase, as long as there is another <<Labyrinth>> SIGNI on your field, the SIGNI in front of this SIGNI gets --4000 power.\n" +
                "@E: Rearrange all of your opponent's SIGNI on the field as you like."
        );

		setName("zh_simplified", "迷阁代号 卢浮宫");
        setDescription("zh_simplified", 
                "@C :攻击阶段期间，你的场上有《エニグマ/メイデン　イオナ》时，这只精灵的正面的精灵的力量-3000。\n" +
                "@C :你的攻击阶段期间，你的场上有其他的<<迷宮>>精灵时，这只精灵的正面的精灵的力量-4000。\n" +
                "@E :对战对手的场上的全部的精灵任意重新配置。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
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
            
            registerConstantAbility(this::onConstEffShared1Cond, new TargetFilter().OP().SIGNI(), new PowerModifier(-3000));
            registerConstantAbility(this::onConstEffShared2Cond, new TargetFilter().OP().SIGNI(), new PowerModifier(-4000));
            
            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onConstEffShared1Cond(CardIndex cardIndex)
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) && cardIndex == getOppositeSIGNI() &&
                    getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("エニグマ/メイデン　イオナ") ? ConditionState.OK : ConditionState.BAD;
        }
        private ConditionState onConstEffShared2Cond(CardIndex cardIndex)
        {
            return isOwnTurn() && GamePhase.isAttackPhase(getCurrentPhase()) && cardIndex == getOppositeSIGNI() &&
                    new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.LABYRINTH).except(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            rearrangeAll(getOpponent());
        }
    }
}
