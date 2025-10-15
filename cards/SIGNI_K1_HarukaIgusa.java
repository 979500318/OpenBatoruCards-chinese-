package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K1_HarukaIgusa extends Card {

    public SIGNI_K1_HarukaIgusa()
    {
        setImageSets("WXDi-CP02-097");

        setOriginalName("伊草ハルカ");
        setAltNames("イグサハルカ Igusa Haruka");
        setDescription("jp",
                "@C：あなたのトラッシュに＜ブルアカ＞のカードが３枚以上あるかぎり、このシグニのパワーは＋5000される。\n" +
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Igusa Haruka");
        setDescription("en",
                "@C: As long as there are three or more <<Blue Archive>> cards in your trash, this SIGNI gets +5000 power.\n@E: Put the top three cards of your deck into your trash.~{{C: This SIGNI gets +4000 power.@@" +
                "~#Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Haruka Igusa");
        setDescription("en_fan",
                "@C: As long as there are 3 or more <<Blue Archive>> cards in your trash, this SIGNI gets +5000 power.\n" +
                "@E: Put the top 3 cards of your deck into the trash." +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "伊草春香");
        setDescription("zh_simplified", 
                "@C :你的废弃区的<<ブルアカ>>牌在3张以上时，这只精灵的力量+5000。\n" +
                "@E :从你的牌组上面把3张牌放置到废弃区。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            registerConstantAbility(this::onConstEff1Cond, new PowerModifier(5000));
            
            registerEnterAbility(this::onEnterEff);

            ConstantAbility cont2 = registerConstantAbility(new PowerModifier(4000));
            cont2.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEff1Cond()
        {
            return new TargetFilter().own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash().getValidTargetsCount() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onEnterEff()
        {
            millDeck(3);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
