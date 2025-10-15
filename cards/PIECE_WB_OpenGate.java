package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockPlayerAbilitySIGNIBarrier;
import open.batoru.game.FieldZone;

public final class PIECE_WB_OpenGate extends Card {

    public PIECE_WB_OpenGate()
    {
        setImageSets("WXDi-P15-003");
        setLinkedImageSets(Token_SIGNIBarrier.IMAGE_SET);

        setOriginalName("ひらけ！ゲート！");
        setAltNames("ヒラケゲート Hirake Geeto");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n" +
                "あなたのシグニゾーン１つに【ゲート】１つを置く。\n\n" +
                "このゲームの間、あなたのセンタールリグは以下の能力を得る。" +
                "@>@A @[エクシード４]@：【シグニバリア】１つを得る。\n" +
                "@A @[エクシード４]@：カードを４枚引く。"
        );

        setName("en", "Unlock! Gate!");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\nPut a [[Gate]] in one of your SIGNI Zones.\nYour Center LRIG gains the following ability for the duration of the game. \n@>@A @[Exceed 4]@: Gain a [[SIGNI Barrier]].\n@A @[Exceed 4]@: Draw four cards."
        );
        
        setName("en_fan", "Open! Gate!");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "Put 1 [[Gate]] on 1 of your SIGNI zones.\n" +
                "This game, your center LRIG gains:" +
                "@>@A @[Exceed 4]@: Gain 1 [[SIGNI Barrier]].\n" +
                "@A @[Exceed 4]@: Draw 4 cards."
        );

		setName("zh_simplified", "开启！大门！");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "你的精灵区1个放置[[大门]]1个。\n" +
                "这场游戏期间，你的核心分身得到以下的能力。（成长后的新的核心分身依然得到能力）\n" +
                "@>@A @[超越 4]@:得到[[精灵屏障]]1个。\n" +
                "@A @[超越 4]@抽4张牌。@@\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.WHITE, CardColor.BLUE);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            FieldZone fieldZone = playerTargetZone(new TargetFilter(TargetHint.ZONE).own().SIGNI().not(new TargetFilter().withZoneObject(CardUnderType.ZONE_GATE))).get();
            attachZoneObject(fieldZone, CardUnderType.ZONE_GATE);
            
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().LRIG(),
                new AbilityGainModifier(this::onAttachedConstEffMod1GetSample),
                new AbilityGainModifier(this::onAttachedConstEffMod2GetSample)
            );
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.permanent());
        }
        private Ability onAttachedConstEffMod1GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(4), this::onAttachedActionEff1);
        }
        private Ability onAttachedConstEffMod2GetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct2 = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(4), this::onAttachedActionEff2);
            attachedAct2.setNestedDescriptionOffset(1);
            return attachedAct2;
        }
        private void onAttachedActionEff1()
        {
            attachPlayerAbility(getOwner(), new StockPlayerAbilitySIGNIBarrier(), ChronoDuration.permanent());
        }
        private void onAttachedActionEff2()
        {
            draw(4);
        }
    }
}
