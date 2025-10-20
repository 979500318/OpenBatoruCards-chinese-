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

public final class SIGNI_B1_EimiIzumimoto extends Card {

    public SIGNI_B1_EimiIzumimoto()
    {
        setImageSets("WX25-CP1-061");

        setOriginalName("和泉元エイミ");
        setAltNames("イズミモトエイミ Izumimoto Eimi");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの手札から＜ブルアカ＞のカードを３枚まで公開してもよい。次の対戦相手のターン終了時まで、このシグニのパワーをこの方法で公開したカード１枚につき＋2000する。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Izumimoto Eimi");

        setName("en_fan", "Eimi Izumimoto");
        setDescription("en",
                "@U: At the end of your turn, you may reveal up to 3 <<Blue Archive>> cards from your hand. Until the end of your opponent's next turn, this SIGNI gets +2000 power for each card revealed this way." +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "和泉元艾米");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，可以从你的手牌把<<ブルアカ>>牌3张最多公开。直到下一个对战对手的回合结束时为止，这只精灵的力量依据这个方法公开的牌的数量，每有1张就+2000。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(2000);

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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.REVEAL).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromHand());
            if(data.get() != null)
            {
                gainPower(getCardIndex(), 2000 * data.size(), ChronoDuration.nextTurnEnd(getOpponent()));
                
                addToHand(data);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
