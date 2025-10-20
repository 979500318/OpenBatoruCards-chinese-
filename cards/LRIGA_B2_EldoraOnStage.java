package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class LRIGA_B2_EldoraOnStage extends Card {

    public LRIGA_B2_EldoraOnStage()
    {
        setImageSets("WXDi-P12-036");

        setOriginalName("エルドラ！オンステージ！");
        setAltNames("エルドラオンステージ Erudora On Suteeji Eldora On");
        setDescription("jp",
                "@E：このターン、１枚目と２枚目にあなたのチェックゾーンに置かれたライフクロスは@>~#：どちらか１つを選ぶ。\n$$1対戦相手のシグニ１体を対象とし、それをダウンする。\n$$2カードを２枚引く。@@@@を得る。"
        );

        setName("en", "Eldora! On Stage!");
        setDescription("en",
                "@E: The first and second Life Cloth put into your Check Zone this turn gain@>~#Choose one -- \n$$1 Down target SIGNI on your opponent's field. \n$$2 Draw two cards."
        );

        setName("en_fan", "Eldora! On Stage!");
        setDescription("en_fan",
                "@E: This turn, the first and second life cloth put into your check zone gain:" +
                "@>~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and down it.\n" +
                "$$2 Draw 2 cards.@@"
        );

		setName("zh_simplified", "艾尔德拉！登场！");
        setDescription("zh_simplified", 
                "@E 这个回合，第1张和第2张放置到你的检查区的生命护甲得到##\n" +
                "@>:以下选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，将其横置。\n" +
                "$$2 抽2张牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ELDORA);
        setColor(CardColor.BLUE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private int count;
        private void onEnterEff()
        {
            count = GameLog.getTurnRecordsCount(event ->
                event.getId() == GameEventId.MOVE && event.getCaller().getLocation() == CardLocation.LIFE_CLOTH &&
                ((EventMove)event).getMoveLocation() == CardLocation.CHECK_ZONE
            );
            
            ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().own().fromCheckZone(), new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getOldLocation() == CardLocation.LIFE_CLOTH && count++ < 2 ? cardIndex.getIndexedInstance().registerLifeBurstAbility(this::onAttachedLifeBurstEff) : null;
        }
        private void onAttachedLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(getOwner(), new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
                down(target);
            } else {
                draw(2);
            }
        }
    }
}
