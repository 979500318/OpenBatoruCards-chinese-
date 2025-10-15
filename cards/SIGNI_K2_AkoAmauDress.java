package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K2_AkoAmauDress extends Card {

    public SIGNI_K2_AkoAmauDress()
    {
        setImageSets("WX25-CP1-088");

        setOriginalName("天雨アコ(ドレス)");
        setAltNames("アマウアコドレス Amau Ako Doresu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの他の＜ブルアカ＞のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、対戦相手のデッキの上からカードを３枚トラッシュに置く。@@を得る。それがレベル３の黒のシグニの場合、それをアップする。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Amau Ako (Dress)");

        setName("en_fan", "Ako Amau (Dress)");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your other <<Blue Archive>> SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, put the top 3 cards of your opponent's deck into the trash.@@" +
                "If it's a level 3 black SIGNI, up it." +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "天雨亚子(礼服)");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的其他的<<ブルアカ>>精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，从对战对手的牌组上面把3张牌放置到废弃区。@@\n" +
                "。其是等级3的黑色的精灵的场合，将其竖直。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

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
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex())).get();
            
            if(target != null)
            {
                attachAbility(target, new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff), ChronoDuration.turnEnd());
                
                if(target.getIndexedInstance().getColor().matches(CardColor.BLACK) && target.getIndexedInstance().getLevelByRef() == 3)
                {
                    up(target);
                }
            }
        }
        private void onAttachedAutoEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().millDeck(getOpponent(), 3);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
