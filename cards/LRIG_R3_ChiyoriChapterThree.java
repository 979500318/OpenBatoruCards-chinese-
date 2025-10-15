package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_R3_ChiyoriChapterThree extends Card {

    public LRIG_R3_ChiyoriChapterThree()
    {
        setImageSets("WX24-P3-018", "WX24-P3-018U");
        setLinkedImageSets("WX24-P3-TK1A");

        setOriginalName("ちより　第三章");
        setAltNames("チヨリダイサンショウ Chiyori Daisanshou");
        setDescription("jp",
                "@U：あなたのメインフェイズ開始時、あなたの場にある、中身が＜トリック＞のシグニである【マジックボックス】を３枚まで表向きにしてシグニにする。あなたのデッキの上からカードを３枚見る。その中からカードを１枚まで【マジックボックス】としてあなたのシグニゾーンに設置し、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A $G1 @[@|ファンタジー|@]@ %R0：クラフトの《転生したらレベル１のママ勇者だったけど無双してみた》１つを場に出す。"
        );

        setName("en", "Chiyori, Chapter Three");
        setDescription("en",
                "@U: At the beginning of your main phase, turn up to 3 <<Trick>> SIGNI that are [[Magic Box]] face up as SIGNI on your field. Look at the top 3 cards of your deck. Put up to 1 card from among them onto 1 of your SIGNI zones as a [[Magic Box]], and put the rest on the bottom of your deck in any order.\n" +
                "@A $G1 @[@|Fantasy|@]@ %R0: Put 1 \"When I was Reincarnated as a Level one Mama Hero, I Tried to be Unparalleled\" craft onto the field."
        );

		setName("zh_simplified", "千依 第三章");
        setDescription("zh_simplified", 
                "@U :你的主要阶段开始时，你的场上有，内部是<<トリック>>精灵的[[魔术箱]]3张最多表向变为精灵。从你的牌组上面看3张牌。从中把牌1张最多作为[[魔术箱]]在你的精灵区设置，剩下的任意顺序放置到牌组最下面。\n" +
                "@A $G1 幻想%R0:衍生的《転生したらレベル１のママ勇者だったけど無双してみた》1只出场。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.CHIYORI);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Fantasy");
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            TargetFilter filter = new TargetFilter().own().withUnderType(CardUnderType.ZONE_MAGIC_BOX).fromSafeLocation(CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT);
            if(isOwnCard(getCardIndex())) filter = filter.withClass(CardSIGNIClass.TRICK);
            DataTable<CardIndex> data = playerTargetCard(0,3, filter);
            flip(data, CardFace.FRONT);
            
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().fromLooked()).get();
            putAsMagicBox(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = craft(getLinkedImageSets().get(0));
            
            if(!putOnField(cardIndex))
            {
                exclude(cardIndex);
            }
        }
    }
}
