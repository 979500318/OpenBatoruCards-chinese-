package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K1_RumiAkeshiro extends Card {

    public SIGNI_K1_RumiAkeshiro()
    {
        setImageSets("WX25-CP1-087");

        setOriginalName("朱城ルミ");
        setAltNames("アケシロルミ Akeshiro Rumi");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの他の＜ブルアカ＞のシグニを２体まで対象とし、それらのシグニ１体につきあなたのデッキの上からカードを１枚トラッシュに置いてもよい。そうした場合、次の対戦相手のターン終了時まで、それらのパワーを＋3000する。" +
                "~{{C：このシグニのパワーは＋4000される。"
        );

        setName("en", "Akeshiro Rumi");

        setName("en_fan", "Rumi Akeshiro");
        setDescription("en",
                "@U: At the end of your turn, target up to 2 of your other <<Blue Archive>> SIGNI, and you may put the top card of your deck into the trash for each of them. If you do, until the end of your opponent's next turn, they get +3000 power." +
                "~{{C: This SIGNI gets +4000 power."
        );

		setName("zh_simplified", "朱城瑠美");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的其他的<<ブルアカ>>精灵2只最多作为对象，可以依据这些精灵的数量，每有1只就从你的牌组上面把1张牌放置到废弃区。这样做的场合，直到下一个对战对手的回合结束时为止，这些的力量+3000。\n" +
                "~{{C:这只精灵的力量+4000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()));
            
            if(data.get() != null && playerChoiceActivate() && getDeckCount(getOwner()) >= data.size() && millDeck(data.size()).size() == data.size())
            {
                gainPower(data, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
    }
}
