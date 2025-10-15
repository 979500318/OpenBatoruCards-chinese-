package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K1_Code2434MuyuAmugase extends Card {

    public SIGNI_K1_Code2434MuyuAmugase()
    {
        setImageSets("WXDi-CP01-045");

        setOriginalName("コード２４３４　天ヶ瀬むゆ");
        setAltNames("コードニジサンジアマガセムユ Koodo Nijisanji Amugase Muyu");
        setDescription("jp",
                "@U：あなたのターン終了時、このシグニがアップ状態の場合、あなたのデッキの上からカードを２枚トラッシュに置く。この方法で＜バーチャル＞のシグニ２枚がトラッシュに置かれた場合、カードを１枚引く。"
        );

        setName("en", "Amagase Muyu, Code 2434");
        setDescription("en",
                "@U: At the end of your turn, if this SIGNI is upped, put the top two cards of your deck into your trash. If exactly two <<Virtual>> SIGNI were put into your trash this way, draw a card."
        );
        
        setName("en_fan", "Code 2434 Muyu Amugase");
        setDescription("en_fan",
                "@U: At the end of your turn, if this SIGNI is upped, put the top 2 cards of your deck into the trash. If 2 <<Virtual>> SIGNI were put into the trash this way, draw 1 card."
        );

		setName("zh_simplified", "2434代号 天瀬梦愈");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这只精灵在竖直状态的场合，从你的牌组上面把2张牌放置到废弃区。这个方法把<<バーチャル>>精灵2张放置到废弃区的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED))
            {
                DataTable<CardIndex> data = millDeck(2);
                
                if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash().match(data).getValidTargetsCount() == 2)
                {
                    draw(1);
                }
            }
        }
    }
}
