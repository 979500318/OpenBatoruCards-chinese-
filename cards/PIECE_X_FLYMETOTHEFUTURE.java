package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_X_FLYMETOTHEFUTURE extends Card {

    public PIECE_X_FLYMETOTHEFUTURE()
    {
        setImageSets("WXDi-P11-001");

        setOriginalName("FLY ME TO THE FUTURE");
        setAltNames("フライミートゥザフューチャー Furai mii tu za Fiichaa");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "以下の２つから１つを選ぶ。\n" +
                "$$1直前のターンにあなたのライフクロスが２枚以上クラッシュされていた場合、対戦相手のシグニ１体を対象とし、それをバニッシュし、カードを２枚引く。\n" +
                "$$2直前のターンにあなたのライフクロスが４枚以上クラッシュされていた場合、あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。"
        );

        setName("en", "Fly Me to the Future");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "Choose one of the following.\n" +
                "$$1 If two or more of your Life Cloth were crushed during the previous turn, vanish target SIGNI on your opponent's field and draw two cards.\n" +
                "$$2 If four or more of your Life Cloth were crushed during the previous turn, shuffle your deck and add the top card of your deck to your Life Cloth."
        );
        
        setName("en_fan", "FLY ME TO THE FUTURE");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 If 2 or more of your life cloth were crushed during the previous turn, target 1 of your opponent's SIGNI, banish it, and draw 2 cards.\n" +
                "$$2 If 4 or more of your life cloth were crushed during the previous turn, shuffle your deck, and add the top card of it to life cloth."
        );

		setName("zh_simplified", "FLY ME TO THE FUTURE");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "从以下的2种选1种。\n" +
                "$$1 在之前的回合你的生命护甲有2张以上被击溃过的场合，对战对手的精灵1只作为对象，将其破坏，抽2张牌。\n" +
                "$$2 在之前的回合你的生命护甲有4张以上被击溃过的场合，你的牌组洗切把最上面的牌加入生命护甲。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
            piece.setModeChoice(1);
        }

        private ConditionState onPieceEffCond()
        {
            return CardAbilities.getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            long count = GameLog.getTurnRecordsCount(getTurnCount()-1, event -> event.getId() == GameEventId.CRUSH && isOwnCard(event.getCaller()));
            if(piece.getChosenModes() == 1)
            {
                if(count >= 2)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                    banish(target);

                    draw(2);
                }
            } else if(count >= 4)
            {
                shuffleDeck();
                addToLifeCloth(1);
            }
        }
    }
}
