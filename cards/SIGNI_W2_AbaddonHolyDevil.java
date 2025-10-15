package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W2_AbaddonHolyDevil extends Card {

    public SIGNI_W2_AbaddonHolyDevil()
    {
        setImageSets("WX24-P3-064");

        setOriginalName("聖魔　アバドン");
        setAltNames("セイマアバドン Seima Abadon");
        setDescription("jp",
                "@C $TP：あなたのトラッシュに＜悪魔＞のシグニが５枚以上あるかぎり、このシグニのパワーは＋5000される。\n" +
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );

        setName("en", "Abaddon, Holy Devil");
        setDescription("en",
                "@C $TP: As long as there are 5 or more <<Devil>> SIGNI in your trash, this SIGNI gets +5000 power.\n" +
                "@E: Put the top 3 cards of your deck into the trash." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );

		setName("zh_simplified", "圣魔 亚巴顿");
        setDescription("zh_simplified", 
                "@C $TP :你的废弃区的<<悪魔>>精灵在5张以上时，这只精灵的力量+5000。\n" +
                "@E :从你的牌组上面把3张牌放置到废弃区。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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

            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
            
            registerEnterAbility(this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromTrash().getValidTargetsCount() >= 5 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            millDeck(3);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
