package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.FieldZone;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneOutlineDetail;

public final class SPELL_K_SlashingMiracle extends Card {

    public SPELL_K_SlashingMiracle()
    {
        setImageSets("WX24-P2-094");

        setOriginalName("スラッシング・ミラクル");
        setAltNames("スラッシングミラクル Surasshingu Mirakuru");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニゾーン１つを指定する。このターン、そのシグニゾーンにあるシグニのパワーを－3000する。\n" +
                "$$2あなたのトラッシュから＜迷宮＞のシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );

        setName("en", "Slashing Miracle");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Choose 1 of your opponent's SIGNI zones. This turn, the SIGNI in that SIGNI zone get --3000 power.\n" +
                "$$2 Target 1 <<Labyrinth>> SIGNI from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power."
        );

		setName("zh_simplified", "震荡·奇迹");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的精灵区1个指定。这个回合，那个精灵区的精灵的力量-3000。\n" +
                "$$2 从你的废弃区把<<迷宮>>精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEff);
            spell.setModeChoice(1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                FieldZone fieldZone = playerTargetZone(new TargetFilter().OP().SIGNI()).get();
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().fromLocation(fieldZone.getZoneLocation()), new PowerModifier(-3000));
                
                ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
                GFX.attachToChronoRecord(record, new GFXZoneOutlineDetail(getOpponent(),fieldZone.getZoneLocation(), "crystal"));
                
                attachPlayerAbility(getOwner(), attachedConst, record);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.LABYRINTH).fromTrash()).get();
                addToHand(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
            gainPower(target, -15000, ChronoDuration.turnEnd());
        }
    }
}
